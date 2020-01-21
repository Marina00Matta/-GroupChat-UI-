import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class Client extends JFrame 
{
	Socket s;
	DataInputStream dis;
	PrintStream ps;
	JTextArea ta;
	String name;
	//JButton b;
	
	public Client ()
	{
		
		this.setLayout(new FlowLayout());
		 ta=new JTextArea(30,50);
		ta.setEditable(false);
		JScrollPane sp = new JScrollPane(ta);
		JTextField tf = new JTextField(40);
		JButton b = new JButton("send");
		
		add(sp);
		add(tf);
		add(b);
		name=JOptionPane.showInputDialog(this,"Enter Name");
		try
		{
			s = new Socket(InetAddress.getLocalHost(),5005);
			dis = new DataInputStream(s.getInputStream());
			ps = new PrintStream(s.getOutputStream());
			
			
		}
		catch(IOException e)
		{
			System.out.println("cant connect");
		}
		Thread read = new Thread(new Runnable() {
                public void run() {
                    String data = null;
                    while (true){
                        try {
                            data = dis.readLine();
                            System.out.println(data);
                        } catch (IOException ex) 
						{
							try
							{
								ps.close();
								dis.close();
								s.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
							finally
							{
								JOptionPane.showMessageDialog(Client.this,"sorry server is out"); 
                            }
							break;
                        }
                        Client.this.ta.append(data+"\n");
                    }
                }   
            });
        read.start();
	
		b.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae)
			{
				if (!(tf.getText().equals("")))
				{
					try
					{
						String s =name +" : " +tf.getText();
						ps.println(s);
						tf.setText("");
					}
					catch(Exception e)
					{
						System.out.println("cant send");
					}
				}
				/*if (!(tf.getText().equals("")))
					ta.append(tf.getText()+"\n");
				tf.setText("");*/
			}
		});
		
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
		/*try
		{
			c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			String rm =dis.readLine();
			ta.append(rm+"\n");
		}
		catch(Exception e)
		{
			System.out.println("no message");
		}
		try 
		{
			ps.close();
			dis.close();
			s.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}*/
		
	
	public static void main ( String[] args)
	{
		Client c=new Client();
		c.setSize(800,1000);
		c.setResizable(false);
		c.setVisible(true);
		
		//new Client();
	}
	
}
		