package org.plasmacore;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

class PlasmacoreSoundManager implements SoundPool.OnLoadCompleteListener
{
  public LookupList<PlasmacoreSound> sounds = new LookupList<PlasmacoreSound>();
  public SoundPool soundPool;
  public boolean   allSoundsPaused;

  public PlasmacoreSoundManager()
  {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
    {
      AudioAttributes attributes = new AudioAttributes.Builder()
        .setUsage( AudioAttributes.USAGE_GAME )
        .setContentType( AudioAttributes.CONTENT_TYPE_SONIFICATION )
        .build();
      soundPool = new SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(8).build();
    }
    else
    {
      soundPool = new SoundPool( 8, AudioManager.STREAM_MUSIC, 0 );
    }

    soundPool.setOnLoadCompleteListener( this );

    final PlasmacoreSoundManager THIS = this;

    Plasmacore.setMessageListener( "Sound.create",
        new PlasmacoreMessageListener()
        {
          public void on( PlasmacoreMessage m )
          {
            String filepath = m.getString( "filepath" );
            boolean isMusic = m.getBoolean( "is_music" );
            isMusic = false; // FIXME
            if (isMusic)
            {
            }
            else
            {
              m.reply().set( "id", sounds.id(new PlasmacoreSound.Effect(THIS,filepath)) );
            }
          }
        }
    );

    Plasmacore.setMessageListener( "Sound.duration",
        new PlasmacoreMessageListener()
        {
          public void on( PlasmacoreMessage m )
          {
            int id = m.getInt( "id" );
            // return: duration:Real64
          }
        }
    );

    Plasmacore.setMessageListener( "Sound.is_playing",
        new PlasmacoreMessageListener()
        {
          public void on( PlasmacoreMessage m )
          {
            int id = m.getInt( "id" );
            // return: is_playing
          }
        }
    );

    Plasmacore.setMessageListener( "Sound.pause",
        new PlasmacoreMessageListener()
        {
          public void on( PlasmacoreMessage m )
          {
            int id = m.getInt( "id" );
          }
        }
    );

    Plasmacore.setMessageListener( "Sound.play",
        new PlasmacoreMessageListener()
        {
          public void on( PlasmacoreMessage m )
          {
            int id = m.getInt( "id" );
            boolean repeating = m.getBoolean( "repeating" );
            PlasmacoreSound sound = sounds.get_by_id( id );
            if (sound != null) sound.play( repeating );
          }
        }
    );

    Plasmacore.setMessageListener( "Sound.position",
        new PlasmacoreMessageListener()
        {
          public void on( PlasmacoreMessage m )
          {
            int id = m.getInt( "id" );
            // return: position:Real64
          }
        }
    );

    Plasmacore.setMessageListener( "Sound.set_position",
        new PlasmacoreMessageListener()
        {
          public void on( PlasmacoreMessage m )
          {
            int id = m.getInt( "id" );
            double position = m.getDouble( "position" );
          }
        }
    );

    Plasmacore.setMessageListener( "Sound.set_volume",
        new PlasmacoreMessageListener()
        {
          public void on( PlasmacoreMessage m )
          {
            int id = m.getInt( "id" );
            double volume = m.getDouble( "volume" );
          }
        }
    );

    Plasmacore.setMessageListener( "Sound.delete",
        new PlasmacoreMessageListener()
        {
          public void on( PlasmacoreMessage m )
          {
            int id = m.getInt( "id" );
          }
        }
    );

  }

  public void onLoadComplete( SoundPool soundPool, int soundID, int status )
  {
    for (int i=0; i<sounds.count(); ++i)
    {
      PlasmacoreSound sound = sounds.get( i );
      if (sound.hasSoundID(soundID))
      {
        sound.onLoadFinished( 0 == status );
        break;
      }
    }
  }

  public void pauseAll()
  {
  }

  public void resumeAll()
  {
  }
}


