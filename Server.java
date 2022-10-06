import java.net.*;

import java.io.*;



class Server

{


    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;



    public Server()
    {
        try {

            server = new ServerSocket(7777);

            System.out.println("server is ready to accept connection");
            System.out.println("Waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startreading();
            startwriting();


            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        

    }
    public void startreading()
    {
        //Thread that has been written in this function will simply read the input data i.e Incoming Client Data.
        Runnable thread_1= ()->{

            System.out.println("Reading Initialized");
            
            try{

                while(true)
                {
                    

                        String msg = br.readLine();

                        if (msg.equals("exit")){

                        System.out.println("Chat Terminated");

                        socket.close();
                        break;
                    }

                    System.out.println("client : " + msg);
                    
                }
            }catch(Exception e) 
            {
                System.out.println("Connection is no Longer VALID");
            }



        };

        new Thread(thread_1).start();

    }


    public void startwriting()
    { 
        //Thread that has been written in this function will simply write the new data that server wants to send to client.
        Runnable Thread_2 = ()->{

            System.out.println("writer Started");


            try{
                while(!socket.isClosed())
                {
                    

                        BufferedReader br_1 = new BufferedReader(new InputStreamReader(System.in));

                        String content =  br_1.readLine();

                        
                        
                        out.println(content);

                        out.flush();   
                        
                        if(content.equals("exit"))
                        {
                            socket.close();
                            break;

                        }
            

                }

            }catch(Exception e)
            {
                e.printStackTrace();
            }

        };

        new Thread(Thread_2).start();

    }

    
    public static void main(String[] args){

        System.out.println("Server Initiated");

        new Server();
    }
}