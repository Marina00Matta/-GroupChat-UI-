import java.io.*;
import java.net.*;
import java.util.Vector ;

public class Server 
{
	ServerSocket ss;
	
	public static void main(String[] args )
	{
		new Server ();
	}
	public Server ()
	{
		
		try
		{
			ss=new ServerSocket(5005);
			while(true)
			{
				
				Socket s = ss.accept();
				new ChatHandler(s);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
class ChatHandler extends Thread
{
	DataInputStream dis ;
	PrintStream ps ;
	String userName;
	static Vector<ChatHandler> clVe=new Vector<ChatHandler>();
	public ChatHandler(Socket cs)
	{
		try
		{
			dis =new DataInputStream(cs.getInputStream());
			ps=new PrintStream(cs.getOutputStream());
			clVe.add(this);
			start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void run()
	{
		while(true)
		{
			try
			{
				
			String msg=dis.readLine();
			userName = msg.substring(0 , msg.indexOf(":"));		
			broadMsg(msg);
			}
			catch(IOException e)
			{
				try 
				{
					clVe.remove(this);
					ps.close();
					dis.close();
				}
				catch(IOException ei)
				{
					ei.printStackTrace();
				}
				finally 
				{
					String oneGo =userName+"is out ";
					broadMsg(oneGo);
					break;
				}
			}
		}
	}
	void broadMsg(String msg)
	{
		for(ChatHandler ch :clVe)
		{
			ch.ps.println(msg);
		}
	}
}
			
	