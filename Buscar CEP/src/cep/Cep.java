package cep;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;

/* Projeto baseado nessas aulas https://www.youtube.com/watch?v=sKO2C58yf28&list=PLbEOwbQR9lqxVuDWNIrG57_JGcbIL3FWP&index=5 */

@SuppressWarnings("serial")
public class Cep extends JFrame {

	private JPanel contentPane;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JComboBox cboUf;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cep frame = new Cep();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cep() {
		setResizable(false);
		setTitle("Localizar CEP");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Cep.class.getResource("/img/home.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("CEP");
		lblNewLabel.setBounds(31, 30, 39, 14);
		contentPane.add(lblNewLabel);

		txtCep = new JTextField();
		txtCep.setBounds(87, 27, 117, 20);
		contentPane.add(txtCep);
		txtCep.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Endere\u00E7o");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 64, 66, 17);
		contentPane.add(lblNewLabel_1);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(87, 61, 299, 20);
		contentPane.add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Bairro");
		lblNewLabel_2.setBounds(31, 98, 46, 14);
		contentPane.add(lblNewLabel_2);

		txtBairro = new JTextField();
		txtBairro.setBounds(87, 95, 299, 20);
		contentPane.add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Cidade");
		lblNewLabel_3.setBounds(31, 129, 46, 14);
		contentPane.add(lblNewLabel_3);

		txtCidade = new JTextField();
		txtCidade.setBounds(87, 126, 201, 20);
		contentPane.add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("UF");
		lblNewLabel_4.setBounds(304, 129, 19, 14);
		contentPane.add(lblNewLabel_4);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(333, 125, 53, 22);
		contentPane.add(cboUf);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(31, 180, 89, 23);
		contentPane.add(btnLimpar);

		JButton btnCep = new JButton("Localizar");
		btnCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtCep.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe o CEP");
					txtCep.requestFocus();
				} else {
					buscarCep();
				}
			}
		});
		btnCep.setBounds(226, 26, 89, 23);
		contentPane.add(btnCep);

		/* Uso da biblioteca Atxy2 para validação do campo txtCep */
		RestrictedTextField validar = new RestrictedTextField(txtCep);

		JLabel lblNewLabel_5 = new JLabel("Criado by Nath\u00E1lia Pavani");
		lblNewLabel_5.setBounds(283, 236, 151, 14);
		contentPane.add(lblNewLabel_5);
		
		lblStatus = new JLabel("");
		lblStatus.setBounds(206, 26, 20, 20);
		contentPane.add(lblStatus);
		validar.setOnlyNums(true);
		validar.setLimit(8);
	}// fim do construtor

	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			 for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
			        Element element = it.next();
			        if (element.getQualifiedName().equals("cidade")) {
			        	txtCidade.setText(element.getText());
			        }
			        if (element.getQualifiedName().equals("bairro")) {
			        	txtBairro.setText(element.getText());
			        }
			        if (element.getQualifiedName().equals("uf")) {
			        	cboUf.setSelectedItem(element.getText());
			        }
			        if (element.getQualifiedName().equals("tipo_logradouro")) {
			        	tipoLogradouro = element.getText();
			        }
			        if (element.getQualifiedName().equals("logradouro")) {
			        	logradouro = element.getText();
			        }
			        if (element.getQualifiedName().equals("resultado")) {
			        	resultado = element.getText();
			        	if(resultado.equals("1")) {
			        		lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png")));
			        	}else {
			        		JOptionPane.showMessageDialog(null, "CEP não encontrado");
			        	}
			        }
			 	}
			 	//setar o campo endereço
			 	txtEndereco.setText(tipoLogradouro + " " + logradouro);
			 
			 } catch (Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * limpar
	 */
	private void limpar() {
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		cboUf.setSelectedItem(null);
		txtCep.requestFocus();
		lblStatus.setIcon(null);
	}
}
