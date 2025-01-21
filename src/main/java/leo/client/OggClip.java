///////////////////////////////////////////////////////////////////////
//      Name:   OggClip.java
//      Desc:   Play an Ogg through OpenAL
//      Date:   1/9/25
//      TODO:
///////////////////////////////////////////////////////////////////////
package leo.client;

import leo.shared.Log;
import org.tinylog.Logger;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.io.BufferedInputStream;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class OggClip implements Runnable {

    /////////////////////////////////////////////////////////////////
    // Properties
    /////////////////////////////////////////////////////////////////

    private byte[] buffer;
    private int bufferID;
    private int sourceID;
    private AudioFormat format;
    private int sampleRate;

    /////////////////////////////////////////////////////////////////
    // Constructor
    /////////////////////////////////////////////////////////////////
    public OggClip(URL url) {

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);

            if (audioStream == null) {
                throw new IOException("Resource not found: " + url.getPath());
            }

            // Get the format of the audio
            format = audioStream.getFormat();

            AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), 16, format.getChannels(), format.getChannels() * 2, format.getSampleRate(), false);
            audioStream = AudioSystem.getAudioInputStream(targetFormat, audioStream);
            format = audioStream.getFormat();

            sampleRate = (int) format.getSampleRate();

            // Read the audio data into a byte array
            buffer = audioStream.readAllBytes();

            //Logger.info("OggClip() - Audio loaded: " + url.getPath() + " Format = " + format + ", Buffer size = " + buffer.length);

            // Load the sound into a buffer
            loadSound();

        } catch (Exception e) {
            Logger.error("OggClip() error loading " + url.getPath() + " : " + e);
        }
    }

    private void loadSound() {
        // Generate a new buffer for this sound
        bufferID = AL10.alGenBuffers();

        // Determine the OpenAL format based on the audio data
        int alFormat = getALFormat(format);

        // Load the audio data into OpenAL buffer
        ByteBuffer byteBuffer = MemoryUtil.memAlloc(buffer.length);
        byteBuffer.put(buffer).flip();
        AL10.alBufferData(bufferID, alFormat, byteBuffer, sampleRate);

        //alBufferData(bufferID, alFormat, MemoryUtil.memAlloc(buffer.length).put(buffer).flip(), sampleRate);

        // Check for OpenAL errors
        int error = AL10.alGetError();
        if (error != AL10.AL_NO_ERROR) {
            Logger.error("OpenAL Error during buffer loading: " + error);
        }

        // Create a new source for this sound
        sourceID = AL10.alGenSources();
        AL10.alSourcei(sourceID, AL10.AL_BUFFER, bufferID);
        
        //set to loop
        AL10.alSourcei(sourceID, AL10.AL_LOOPING, AL10.AL_TRUE);

        // Turn off positional sound
        AL10.alSourcei(sourceID, AL10.AL_SOURCE_RELATIVE, AL10.AL_TRUE);
    }

    private int getALFormat(AudioFormat format) {
        // Determine the OpenAL format based on the AudioFormat
        boolean isSigned = format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED;
        int channels = format.getChannels();
        int sampleSizeInBits = format.getSampleSizeInBits();

        if (channels == 1) { // Mono
            if (sampleSizeInBits == 8) {
                return AL10.AL_FORMAT_MONO8; // OpenAL treats all 8-bit as unsigned
            } else if (sampleSizeInBits == 16) {
                if (isSigned) {
                    return AL10.AL_FORMAT_MONO16;
                } else {
                    throw new IllegalArgumentException("16-bit unsigned is not supported.");
                }
            }
        } else if (channels == 2) { // Stereo
            if (sampleSizeInBits == 8) {
                return AL10.AL_FORMAT_STEREO8; // OpenAL treats all 8-bit as unsigned
            } else if (sampleSizeInBits == 16) {
                if (isSigned) {
                    return AL10.AL_FORMAT_STEREO16;
                } else {
                    throw new IllegalArgumentException("16-bit unsigned is not supported.");
                }
            }
        }

        throw new IllegalArgumentException("Unsupported audio format: " + format);
    }

    /////////////////////////////////////////////////////////////////
    // Play the sound
    /////////////////////////////////////////////////////////////////
    public void play() {
        try {
            Thread runner = new Thread(this, "MusicPlayThread-" + System.currentTimeMillis());
            runner.start();
        } catch (Exception e) {
            Logger.error("Sound.play(): " + e);
        }
    }

    public void run() {
        try {
            // Play the sound
            AL10.alSourcePlay(sourceID);

            // Wait for the sound to finish
            while (AL10.alGetSourcei(sourceID, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING) {
                Thread.sleep(100);
            }

            // Clean up after playing the sound
            AL10.alSourceStop(sourceID);
            //AL10.alDeleteSources(sourceID);
        } catch (Exception e) {
            Logger.error("Sound.run(): " + e);
        }
    }

    public void pause() {
        //Logger.info("pausing ogg");
        AL10.alSourcePause(sourceID);
    }

    public void resume() {
        //Logger.info("resume ogg");
        AL10.alSourcePlay(sourceID);
    }

    public void stop() {
        //Logger.info("stopping ogg");
        AL10.alSourceStop(sourceID);
        AL10.alSourceRewind(sourceID);
    }

    public void close() {
        //Logger.info("closing ogg");
        AL10.alSourceStop(sourceID);
        AL10.alDeleteSources(sourceID);
    }
    public void setVolume(int volume) {
        AL10.alSourcef(sourceID, AL10.AL_GAIN, volume / 5.0f);
    }   

}
