package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import view.App;

public class TelaCRUDController {

	static Socket clienteSocket = null;
	private Gson gson = new Gson();
	private String tokenSessao;
	
	
    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnCadastrar;
    
    @FXML
    private Button btnConsultar;


    @FXML
    private Button btnDeslogar;

    @FXML
    private Button btnEnviarMensagem;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnLogar;
    
    @FXML
    private Button btnSairServidor;
    
    @FXML
    private Label lblNome;

    @FXML
    private Label lblSenha;

    @FXML
    private Label lblToken;

    @FXML
    private Label lblUsuario;
    
    @FXML
    private Label lblUsuarioLogado;

    @FXML
    private TextArea txtArea;
    
    @FXML
    private TextField txtFieldNome;

    @FXML
    private TextField txtFieldSenha;

    @FXML
    private TextField txtFieldToken;

    @FXML
    private TextField txtFieldUsuario;
    

	@FXML
    void acaoAtualizar(ActionEvent event) {
    	try {
			PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(),true);
    		BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
			JsonObject request = new JsonObject();
			
			if(lblUsuarioLogado.getText().equals("admin") && !txtFieldUsuario.getText().isEmpty()) {
				request.addProperty("op", "atualizarUsuarioAdmin");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token_admin", txtFieldToken.getText());
					request.addProperty("usuario", txtFieldUsuario.getText());
					request.addProperty("nome", txtFieldNome.getText());
					request.addProperty("senha", txtFieldSenha.getText());
					
		    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
		    		
					saida.println(gson.toJson(request));
					
		    		String response = entrada.readLine();
		    		
		    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
		    		
				}else {
					request.addProperty("token_admin", tokenSessao);
					request.addProperty("usuario", txtFieldUsuario.getText());
					request.addProperty("nome", txtFieldNome.getText());
					request.addProperty("senha", txtFieldSenha.getText());
					
		    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
		    		
					saida.println(gson.toJson(request));
					
		    		String response = entrada.readLine();
		    		
		    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
		    		
				}	
			}else {
				request.addProperty("op", "atualizarUsuario");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token", txtFieldToken.getText());
					request.addProperty("nome", txtFieldNome.getText());
					request.addProperty("senha", txtFieldSenha.getText());
					
		    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
		    		
					saida.println(gson.toJson(request));
					
		    		String response = entrada.readLine();
		    		
		    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
		    		
				}else {
					request.addProperty("token", tokenSessao);
					request.addProperty("nome", txtFieldNome.getText());
					request.addProperty("senha", txtFieldSenha.getText());
					
		    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
		    		
					saida.println(gson.toJson(request));
					
		    		String response = entrada.readLine();
		    		
		    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
		    		
				}
			}
			limparCampos();
		} catch (IOException e) {
			System.err.println("erro: " + e.getMessage());
		}
    }

    @FXML
    void acaoCadastrar(ActionEvent event) {
    	try {
    		PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(), true);
    		BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
    		JsonObject request = new JsonObject();
    		String response = null;
    		
    		request.addProperty("op", "cadastrarUsuario");
    		request.addProperty("nome", txtFieldNome.getText());
    		request.addProperty("usuario", txtFieldUsuario.getText());
    		request.addProperty("senha", txtFieldSenha.getText());
    		
    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
    		
    		saida.println(gson.toJson(request));

    		response = entrada.readLine();
    		
    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
    		
    		limparCampos();
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void acaoConsultar(ActionEvent event) {
    	try {
			PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(),true);
    		BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
			JsonObject request = new JsonObject();
			
			if(lblUsuarioLogado.getText().equals("admin") && txtFieldUsuario.getText().isEmpty()) {
				request.addProperty("op", "consultarUsuariosAdmin");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token_admin", txtFieldToken.getText());
					
		    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
		    		
					saida.println(gson.toJson(request));
					
		    		String response = entrada.readLine();
		    		
		    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
		    		
				}else {
					request.addProperty("token_admin", tokenSessao);
					
		    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
		    		
					saida.println(gson.toJson(request));
					
		    		String response = entrada.readLine();
		    		
		    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
		    		
				}	
			}else if(lblUsuarioLogado.getText().equals("admin") && !txtFieldUsuario.getText().isEmpty()){
				request.addProperty("op", "consultarUsuarioAdmin");
				
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token_admin", txtFieldToken.getText());
					request.addProperty("usuario", txtFieldUsuario.getText());
					
		    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
		    		
					saida.println(gson.toJson(request));
					
		    		String response = entrada.readLine();
		    		
		    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
		    		
				}else {
					request.addProperty("token_admin", tokenSessao);
					request.addProperty("usuario", txtFieldUsuario.getText());
					
		    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
		    		
					saida.println(gson.toJson(request));
					
		    		String response = entrada.readLine();
		    		
		    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
		    		
				}
			}else {
				request.addProperty("op", "consultarUsuario");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token", txtFieldToken.getText());
					
		    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
		    		
					saida.println(gson.toJson(request));
					
		    		String response = entrada.readLine();
		    		
		    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
		    		
				}else {
					request.addProperty("token", tokenSessao);
					
		    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
		    		
					saida.println(gson.toJson(request));
					
		    		String response = entrada.readLine();
		    		
		    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
		    		
				}
			}
			limparCampos();
		} catch (IOException e) {
			System.err.println("erro: " + e.getMessage());
		}
    }

    @FXML
    void acaoDeslogar(ActionEvent event) {
    	try {
			PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(),true);
    		BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
			JsonObject request = new JsonObject();

			request.addProperty("op", "logout");
			if(!txtFieldToken.getText().isEmpty()) {
				request.addProperty("token", txtFieldToken.getText());
			}else {
				request.addProperty("token", tokenSessao);
			}

    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
    		
    		saida.println(gson.toJson(request));

    		String response = entrada.readLine();
    		
    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
			lblUsuarioLogado.setText("...");
			limparCampos();
		} catch (IOException e) {
			System.err.println("erro: " + e.getMessage());
		}
    }
    
    @FXML
    void acaoEncerrarServidor(ActionEvent event) {
    	try {
			clienteSocket.close();
			App.setRoot("TelaInicio");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void acaoEnviarMensagem(ActionEvent event) {

    }

    @FXML
    void acaoExcluir(ActionEvent event) {
    	try {
			PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(),true);
    		BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
			JsonObject request = new JsonObject();
			
			if(lblUsuarioLogado.getText().equals("admin") && !txtFieldUsuario.getText().isEmpty()) {
				request.addProperty("op", "deletarUsuarioAdmin");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token_admin", txtFieldToken.getText());
					request.addProperty("usuario", txtFieldUsuario.getText());
					
		    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
		    		
					saida.println(gson.toJson(request));
					
		    		String response = entrada.readLine();
		    		
		    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
		    		
				}else {
					request.addProperty("token_admin", tokenSessao);
					request.addProperty("usuario", txtFieldUsuario.getText());
					
		    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
		    		
					saida.println(gson.toJson(request));
					
		    		String response = entrada.readLine();
		    		
		    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
		    		
				}	
			}else {
				request.addProperty("op", "deletarUsuario");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token", txtFieldToken.getText());
				}else {
					request.addProperty("token", tokenSessao);
				}
				request.addProperty("nome", txtFieldNome.getText());
				request.addProperty("senha", txtFieldSenha.getText());
				
	    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
	    		
	    		saida.println(gson.toJson(request));

	    		String response = entrada.readLine();
	    		
	    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
	    		limparCampos();
			}
		} catch (IOException e) {
			System.err.println("erro: " + e.getMessage());
		}
    }

    @FXML
    void acaoLogar(ActionEvent event) {
		try {
			PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(),true);
    		BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
			JsonObject request = new JsonObject();
			
			request.addProperty("op", "login");
			request.addProperty("usuario", txtFieldUsuario.getText());
			request.addProperty("senha", txtFieldSenha.getText());
			
    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
    		
    		saida.println(gson.toJson(request));

    		String response = entrada.readLine();
    		
    		txtArea.appendText(" recebido do servidor" + response + "\n\n");
    		
			JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
			if (jsonResponse.has("resposta") && jsonResponse.get("resposta").getAsString().equals("200")) {
				if(jsonResponse.has("token")) {
					this.tokenSessao = jsonResponse.get("token").getAsString();
					lblUsuarioLogado.setText(txtFieldUsuario.getText());
				}else if(jsonResponse.has("token_admin")) {
					this.tokenSessao = jsonResponse.get("token_admin").getAsString();
					lblUsuarioLogado.setText(txtFieldUsuario.getText());
				}
			}
			limparCampos();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void limparCampos() {
    	txtFieldNome.clear();
    	txtFieldSenha.clear();
    	txtFieldUsuario.clear();
    	txtFieldToken.clear();
    }
}
