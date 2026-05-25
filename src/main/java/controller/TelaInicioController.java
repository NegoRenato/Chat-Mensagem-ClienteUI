package controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import view.App;

public class TelaInicioController {
	
	Socket clientSocket = null;
    @FXML
    private Button btnConectar;

    @FXML
    private Label lblTxtIP;

    @FXML
    private Label lblTxtInicio;

    @FXML
    private Label lblTxtPorta;

    @FXML
    private VBox panel;

    @FXML
    private TextField txtFieldIp;

    @FXML
    private TextField txtFieldPorta;

    @FXML
    void acaoBtn(MouseEvent event) {
    	instanciarSocketCliente();
    }
    
    public void instanciarSocketCliente() {
    	try {
        	String serverIp = txtFieldIp.getText();
        	int porta = Integer.parseInt(txtFieldPorta.getText());
        	
			clientSocket = new Socket(serverIp,porta);
			
			TelaCRUDController.clienteSocket = clientSocket;
			
			App.setRoot("TelaCRUD");
			
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Sucesso");
			alerta.setHeaderText("Conectado ao servidor");
			alerta.setContentText("IP: " + serverIp + " PORTA: " + porta);
		    alerta.showAndWait();
			
		} catch (UnknownHostException e) {
			Platform.runLater(() ->{
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("ERRO");
				alerta.setHeaderText("Servidor Inexistente");
				alerta.setContentText("não foi possivel conectar-se ao servidor");
			    alerta.showAndWait();
			});
		} catch (IOException e) {
			Platform.runLater(() ->{
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("ERRO");
				alerta.setHeaderText("Servidor nao encontrado");
				alerta.setContentText("Erro: " + e.getMessage());
			    alerta.showAndWait();
			});
		} catch (NumberFormatException e) {
			Platform.runLater(() ->{
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("ERRO");
				alerta.setHeaderText("Formato invalido");
				alerta.setContentText("Digite os valores corretamente");
			    alerta.showAndWait();
			});
		}
    }

}
