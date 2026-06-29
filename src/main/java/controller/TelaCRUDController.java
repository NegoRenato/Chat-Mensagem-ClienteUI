package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

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
	private String ultimoUsuarioLogado;
	private boolean threadLeituraIniciada = false;

    @FXML
    public void initialize() {
        if (clienteSocket != null && !threadLeituraIniciada) {
            iniciarThreadLeitura();
        }
    }

    public void iniciarThreadLeitura() {
        threadLeituraIniciada = true;
        Thread thread = new Thread(() -> {
            try {
                BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                String response;
                while ((response = entrada.readLine()) != null) {
                    final String res = response;
                    javafx.application.Platform.runLater(() -> processarResposta(res));
                }
            } catch (IOException e) {
                javafx.application.Platform.runLater(() -> txtArea.appendText("Conexão com servidor encerrada.\n"));
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void processarResposta(String response) {
        try {
            JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
            
            if (jsonResponse.has("op") && jsonResponse.get("op").getAsString().equals("receberMensagem")) {
                String remetente = jsonResponse.get("remetente").getAsString();
                String msg = jsonResponse.get("mensagem").getAsString();
                txtArea.appendText("Mensagem de " + remetente + ": " + msg + "\n\n");
                return;
            } 
            
            if (jsonResponse.has("lista_usuarios")) {
                JsonArray usuariosArray = jsonResponse.getAsJsonArray("lista_usuarios");
                ObservableList<String> usuarios = FXCollections.observableArrayList();
                for (JsonElement element : usuariosArray) {
                    usuarios.add(element.getAsString());
                }
                if (colunaUsuarios != null) {
                    colunaUsuarios.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
                }
                if (tabelaUsuarios != null) {
                    tabelaUsuarios.setItems(usuarios);
                }
                txtArea.appendText(" recebido do servidor: " + response + "\n\n");
                return;
            } 
            
            if (jsonResponse.has("token")) {
                this.tokenSessao = jsonResponse.get("token").getAsString();
                if (ultimoUsuarioLogado != null) {
                    lblUsuarioLogado.setText(ultimoUsuarioLogado);
                }
                txtArea.appendText(" recebido do servidor: " + response + "\n\n");
                return;
            }

            if (jsonResponse.has("resposta") && jsonResponse.get("resposta").getAsString().equals("401")) {
                txtArea.appendText("Erro do servidor: " + jsonResponse.get("mensagem").getAsString() + "\n\n");
                return;
            }

            txtArea.appendText(" recebido do servidor: " + response + "\n\n");
            
        } catch (Exception e) {
            txtArea.appendText(" recebido do servidor: " + response + "\n\n");
        }
    }
	
	
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
    private TableView<String> tabelaUsuarios;

    @FXML
    private TableColumn<String, String> colunaUsuarios;

    @FXML
    private Button btnListarUsuarios;

    @FXML
    private Button btnLimparTexto;

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
			JsonObject request = new JsonObject();
			
			if(lblUsuarioLogado.getText().equals("admin") && !txtFieldUsuario.getText().isEmpty()) {
				request.addProperty("op", "atualizarUsuarioAdmin");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token", txtFieldToken.getText());
					request.addProperty("usuario", txtFieldUsuario.getText());
					request.addProperty("nome", txtFieldNome.getText());
					request.addProperty("senha", txtFieldSenha.getText());
				}else {
					request.addProperty("token", tokenSessao);
					request.addProperty("usuario", txtFieldUsuario.getText());
					request.addProperty("nome", txtFieldNome.getText());
					request.addProperty("senha", txtFieldSenha.getText());
				}	
			}else {
				request.addProperty("op", "atualizarUsuario");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token", txtFieldToken.getText());
					request.addProperty("nome", txtFieldNome.getText());
					request.addProperty("senha", txtFieldSenha.getText());
				}else {
					request.addProperty("token", tokenSessao);
					request.addProperty("nome", txtFieldNome.getText());
					request.addProperty("senha", txtFieldSenha.getText());
				}
			}
			txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
			saida.println(gson.toJson(request));
			limparCampos();
		} catch (IOException e) {
			System.err.println("erro: " + e.getMessage());
		}
    }

    @FXML
    void acaoCadastrar(ActionEvent event) {
    	try {
    		PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(), true);
    		JsonObject request = new JsonObject();
    		
    		request.addProperty("op", "cadastrarUsuario");
    		request.addProperty("nome", txtFieldNome.getText());
    		request.addProperty("usuario", txtFieldUsuario.getText());
    		request.addProperty("senha", txtFieldSenha.getText());
    		
    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
    		saida.println(gson.toJson(request));
    		limparCampos();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void acaoConsultar(ActionEvent event) {
    	try {
			PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(),true);
			JsonObject request = new JsonObject();
			
			if(lblUsuarioLogado.getText().equals("admin") && txtFieldUsuario.getText().isEmpty()) {
				request.addProperty("op", "consultarUsuariosAdmin");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token", txtFieldToken.getText());
				}else {
					request.addProperty("token", tokenSessao);
				}	
			}else if(lblUsuarioLogado.getText().equals("admin") && !txtFieldUsuario.getText().isEmpty()){
				request.addProperty("op", "consultarUsuarioAdmin");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token", txtFieldToken.getText());
					request.addProperty("usuario", txtFieldUsuario.getText());
				}else {
					request.addProperty("token", tokenSessao);
					request.addProperty("usuario", txtFieldUsuario.getText());
				}
			}else {
				request.addProperty("op", "consultarUsuario");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token", txtFieldToken.getText());
				}else {
					request.addProperty("token", tokenSessao);
				}
			}
			txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
			saida.println(gson.toJson(request));
			limparCampos();
		} catch (IOException e) {
			System.err.println("erro: " + e.getMessage());
		}
    }

    @FXML
    void acaoDeslogar(ActionEvent event) {
    	try {
			PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(),true);
			JsonObject request = new JsonObject();

			request.addProperty("op", "logout");
			if(!txtFieldToken.getText().isEmpty()) {
				request.addProperty("token", txtFieldToken.getText());
			}else {
				request.addProperty("token", tokenSessao);
			}

    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
    		saida.println(gson.toJson(request));
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
			e.printStackTrace();
		}
    }

    @FXML
    void acaoEnviarMensagem(ActionEvent event) {
        try {
            PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(), true);
            JsonObject request = new JsonObject();
            request.addProperty("op", "enviarMensagem");
            
            if(!txtFieldToken.getText().isEmpty()) {
                request.addProperty("token", txtFieldToken.getText());
            } else {
                request.addProperty("token", tokenSessao);
            }
            
            String destinatario = null;
            if (tabelaUsuarios != null && tabelaUsuarios.getSelectionModel().getSelectedItem() != null) {
                destinatario = tabelaUsuarios.getSelectionModel().getSelectedItem();
            }
            
            if (destinatario == null || destinatario.isEmpty()) {
                request.addProperty("destinatario", "/todos");
            } else {
                request.addProperty("destinatario", destinatario);
            }
            
            request.addProperty("mensagem", txtArea.getText());
            
            txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
            saida.println(gson.toJson(request));
        } catch (IOException e) {
            System.err.println("erro: " + e.getMessage());
        }
    }

    @FXML
    void acaoListarUsuariosLogados(ActionEvent event) {
        listarUsuariosLogados();
    }

    public void listarUsuariosLogados() {
        try {
            PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(), true);
            JsonObject request = new JsonObject();
            
            request.addProperty("op", "listarUsuariosLogados");
            if(!txtFieldToken.getText().isEmpty()) {
                request.addProperty("token", txtFieldToken.getText());
            } else {
                request.addProperty("token", tokenSessao);
            }
            
            txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
            saida.println(gson.toJson(request));
        } catch (IOException e) {
            System.err.println("erro: " + e.getMessage());
        }
    }

    @FXML
    void acaoExcluir(ActionEvent event) {
    	try {
			PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(),true);
			JsonObject request = new JsonObject();
			
			if(lblUsuarioLogado.getText().equals("admin") && !txtFieldUsuario.getText().isEmpty()) {
				request.addProperty("op", "deletarUsuarioAdmin");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token", txtFieldToken.getText());
					request.addProperty("usuario", txtFieldUsuario.getText());
				}else {
					request.addProperty("token", tokenSessao);
					request.addProperty("usuario", txtFieldUsuario.getText());
				}	
			}else {
				request.addProperty("op", "deletarUsuario");
				if(!txtFieldToken.getText().isEmpty()) {
					request.addProperty("token", txtFieldToken.getText());
				}else {
					request.addProperty("token", tokenSessao);
				}
			}
			txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
			saida.println(gson.toJson(request));
			limparCampos();
		} catch (IOException e) {
			System.err.println("erro: " + e.getMessage());
		}
    }

    @FXML
    void acaoLogar(ActionEvent event) {
		try {
			PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(),true);
			JsonObject request = new JsonObject();
			
			request.addProperty("op", "login");
			request.addProperty("usuario", txtFieldUsuario.getText());
			request.addProperty("senha", txtFieldSenha.getText());
			
			ultimoUsuarioLogado = txtFieldUsuario.getText();

    		txtArea.appendText("enviado ao servidor: " + gson.toJson(request) + "\n\n");
    		saida.println(gson.toJson(request));
			limparCampos();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void acaoLimparTexto(ActionEvent event) {
        txtArea.clear();
    }
    
    public void limparCampos() {
    	txtFieldNome.clear();
    	txtFieldSenha.clear();
    	txtFieldUsuario.clear();
    	txtFieldToken.clear();
    }
}
