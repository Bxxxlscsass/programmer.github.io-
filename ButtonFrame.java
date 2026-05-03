package empresa;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ButtonFrame extends JFrame {
	private final JButton plainJButton;
	private final JButton fancyJButton; 
	private final JProgressBar barraProgresso; // A barra de carregamento

	//buttonFrame adiciona JButtons ao JFrame
	public ButtonFrame() {
		super("Console de Suporte - Allan Felipe");
		setLayout(new FlowLayout());
		
		plainJButton = new JButton("Cadastro"); //botao com texto 
		add(plainJButton); //adiciona plainJButton ao JFrame
		
		
		//botão "fancy usando simbolos de texto"
		fancyJButton = new JButton("Administrador"); 
		fancyJButton.set.ToolTipText("Acesso restrito ao sistema"); 
		add(fancyJButton);
		
		//inicializa a barra carregamento (escondida por padrão)
		barraProgresso = new JProgressBar(0, 100);
		barraProgresso.setStringPainted(true);
		barraProgresso.setVisible(false);
		add(barraProgresso);

		//cria novo ButtonHandler de tratamento para tratamento de evento 
		ButtonHandler handler = new ButtonHandler();
		fancyJButton.addActionListener(handler);
		fancyJButton.addActionListener(handler);
		
	}
	private class ButtonHandler implements ActionListener {
		@Override 
		public void actionPerformed(ActionEvent event) {
			String comando = event.getActionCommand();

			//simular carregamento de abrir as funções
			simularCarregamento(() -> {
				if (comando.equals("Cadastro")) {
					exibirMenuCadastro();
				} else if (comando.equals("Administrador")) {
					exibirLoginAdmin();
				}
			});

		}

		//criamos uma nova thread para não travar o sistema (multithreading)
		private void simularCarregamento(Runnable acaoPosCarregamento) {
			barraProgresso.setVisible(true);
			barraProgresso.setValue(0);

			new Thread(() -> {
				try {
					for (int i = 0; i <= 100; i += 20) {
						final int progresso = i;
						SwingUtilities.invokeLater(() -> barraProgresso.setValue(progresso));
						Thread.sleep(200);  //simula atraso de rede/processamento
					}
					SwingUtilities.invokeLater(() -> {
						barraProgresso.setVisible(false);
						acaoPosCarregamento.run(); //abre o menu após carregar
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}

		// ----Menu de cadastro de usuário ---
		 private void exibirMenuCadastro() {
			JTextField nomeUser = new JTextField();
			JTextField emailUser = new JTextField();
			JTextField senhaUser = new JPasswordTextField();  //campo de senha corrigido 

		Object[] formulario = {
			"Nome Completo:", nomeUser,
			"Email:", emailUser,
			"Senha:", senhaUser
		};

		int resultado = JOptionPane.showConfirmDialog(null, formulario,
			"Cadastro de Novo Usuário de TI", JOptionPane.OK_CANCEL_OPTION
		);

		if (resultado == JOptionPane.OK_OPTION) {
			//simulação de salvamento em banco de dados 
			String resumo = String.format("Usuário: %s\nE-mail: %s\nStatus: Cirado com Sucesso!",
			                nomeUser.getText(), emailUser.getText());
			JOptionPane.showMessageDialog(ButtonFrame.this, resumo);
			System.out.println("[LOG] Novo cadastro realizado: " + emailUser.getText());
		}
	}
		//--login administrador ---
		private void exibirLoginAdmin() {
			JPasswordField senhaAdmin = new JPasswordField();
			Object[] login = {"Senha do Admistrador:", senhaAdmin};

			int resultado = JOptionPane.showConfirmDialog(null, login,
				"Autenticação Requerida", JOptionPane.OK_CANCEL_OPTION);

			if (resultado == JOptionPane.OK_OPTION) {
				String senha = new String(senhaAdmin.getPassword());
				if (senha.equals("admin123")) {
					JOptionPane.showMessageDialog(ButtonFrame.this, "Acesso root concedido. Sistema Ok.");
				
				} else {
				JOptionPane.showMessageDialog(ButtonFrame.this, "Senha incorreta. Acesso negado.");
				}		
			}
		}
	}

}
	

