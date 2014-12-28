import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Musica {
private BasicPlayer player;
String ruta ="";
InputStream in;
AudioStream audio;
public Musica() {
    player = new BasicPlayer();
    
   
}

public void Play(){
AudioPlayer.player.start(audio);
}

public void AbrirFichero(String ruta) throws IOException{
	this.ruta=ruta;
in =new FileInputStream(ruta);
audio = new AudioStream(in);

}

public void Pausa(){
AudioPlayer.player.interrupt();
}

public void Continuar() {
	if(AudioPlayer.player.isInterrupted()){
		AudioPlayer.player.run();
	}
}

public void Stop() {
   
}

	

}
