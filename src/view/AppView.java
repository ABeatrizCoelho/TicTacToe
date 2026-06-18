package view;

import app.AppTicTacToe.GameType;
import util.Console;

public class AppView {
	
	static final String GAMETYPE_MESSAGE = """
			Qual o tipo de jogo que você deseja? 
			      1 : Pessoa x Pessoa
			      2 : Pessoa x Alienígena
			
			""";

	public GameType askForGameType() {		
		
		int type = Console.readInt(GAMETYPE_MESSAGE, 1, 2);
		return GameType.values()[type-1];
	}

	static final String WELCOME_MESSAGE = """
			  TIC - TAC - TOE
			
			    _X_|_O_|_O_
			    _O_|_X_|_X_
			     X | O | O
			  
			     POO - TSI
			   UTFPR  TOLEDO
			""";
	
	public void showWelcomeMessage() {
		
		Console.println(WELCOME_MESSAGE);
		Console.readLine("Para iniciar pressione 'ENTER'");
	}
	
	static final String GOODBYE_MESSAGE = """
			    OBRIGADO!
			""";
	
	public void showGoodbyeMessage() {
		Console.println(GOODBYE_MESSAGE);
	}

	public boolean askForNewGame() {

		char answer = Console.readChar("Jogar novamente? (S/N): ", 'S','s','N','n');
		return (answer == 'S' || answer == 's');
	}

}