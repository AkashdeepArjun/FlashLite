import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class Home extends Application {

    Button partition_flash_button;

    CheckBox item_recovery;

    static ProgressIndicator partition_progress_bar;

    Assistant ally = new Assistant();

    Recovery_flasher recovery_flasher = new Recovery_flasher();
    static TextArea output;

   static String[] task ={"/bin/bash","-c","heimdall detect"};


    static String[] full_flash = {"/bin/bash","-c",". /$HOME/flash_full.sh"};

    static String[] flash_recovery_only ={"/bin/bash","-c","./flash_recovery.sh"};
    


    public  static ProcessBuilder flash_recovery_setup = new ProcessBuilder(flash_recovery_only);


    public static ProcessBuilder full_flash_setup = new ProcessBuilder(full_flash);

    public static Process begin_full_flash;


    public static ProgressBar progress;

    public static void main(String[] parameters)
    {


            launch(parameters);


        }


    @Override
    public void start(Stage primaryStage) throws Exception {

         Process process;

            String line;



            BufferedReader reader;

            primaryStage.setMaxWidth(852);

            primaryStage.setMaxHeight(500);

            ProcessBuilder full_flasher_handler = new ProcessBuilder(full_flash);


        try {


            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("Home.fxml"));
            
            AnchorPane custom_flash_layout = (AnchorPane)FXMLLoader.load(getClass().getClassLoader().getResource("custom_flash.fxml"));

            item_recovery =(CheckBox)custom_flash_layout.lookup("#item_recovery");


            partition_progress_bar =(ProgressIndicator)custom_flash_layout.lookup("#partition_progress");

            partition_flash_button =(Button)custom_flash_layout.lookup("#partition_flash_button");

            partition_flash_button.setOnMouseEntered(blabla->{

                partition_flash_button.setEffect(new Glow(0.5));


            });

            partition_flash_button.setOnMouseExited(blabla->{

                partition_flash_button.setEffect(new Glow(0));

            });

            partition_flash_button.setOnAction(lol ->
            {


                if(item_recovery.isSelected())

                {


                        recovery_flasher.execute();

                }


            });
            Scene scene = new Scene(root);


            Scene scene_custom_flash = new Scene(custom_flash_layout);





            AnchorPane flash_stuff=(AnchorPane)FXMLLoader.load(getClass().getResource("flash_system.fxml")) ;

            progress = (ProgressBar)flash_stuff.lookup("#progress");

            Button hard_flash_button = (Button)flash_stuff.lookup("#hard_flash_button");

            Button custom_flash_button = (Button)flash_stuff.lookup("#custom_flash_button");


            custom_flash_button.setOpacity(hard_flash_button.getOpacity());

            custom_flash_button.setOnMouseEntered(bluff->
            {

                custom_flash_button.setEffect(new Glow(0.3));

            });

            output = (TextArea)flash_stuff.lookup("#output_console");


            custom_flash_button.setOnAction(blabla->{


                primaryStage.setScene(scene_custom_flash);





            });

            custom_flash_button.setOnMouseClicked(lala->
            {


                custom_flash_button.setEffect(new Lighting());

            });


            custom_flash_button.setOnMouseExited(bluff->{

                custom_flash_button.setEffect(new Glow(0));

            });


            hard_flash_button.setOnMouseEntered(doing ->

                    {

                        hard_flash_button.setEffect(new Glow(0.3));


                    }



            );

            hard_flash_button.setOnMouseExited(doing->{


                hard_flash_button.setEffect(new Glow(0));


            });

            hard_flash_button.setOnAction(handling ->
                    {

                        ally.execute();
                    });

            Scene scene2 = new Scene(flash_stuff);

            Button flash_button = (Button)scene.lookup("#flash_button");

            flash_button.setOnMouseEntered(blabla->
            {

                flash_button.setEffect(new Glow(0.4));

            });


            flash_button.setOnMouseExited(bla->
            {

                flash_button.setEffect(new Glow(0));

            });


            flash_button.setOnAction(blavla-> primaryStage.setScene(scene2));


            Button button = (Button)scene.lookup("#detect_button");

            button.setOnMouseEntered(lol->
            {


                button.setEffect(new Glow(0.4));

            });


            button.setOnMouseExited(ul->
            {


                button.setEffect(new Glow(0));

            });

            ImageView signal =(ImageView)scene.lookup("#detect_signal");




            ImageView error = (ImageView)scene.lookup("#error_signal");

            ProcessBuilder task_handler = new ProcessBuilder(task);

            TextArea console = (TextArea)scene.lookup("#output_console");




            button.setOnAction(handling ->



                    {

                        try
                        {
                             final  Process process1 = task_handler.start();


                              process1.waitFor();

                             int status = process1.exitValue();


                             if(status==0)
                             {

                                 signal.setOpacity(1);
                             }

                             else
                             {

                                 error.setOpacity(1);
                             }








                        }catch(Exception assistant){assistant.printStackTrace();


            }});



                        primaryStage.setScene(scene);


            primaryStage.show();

        }catch (Exception e){e.printStackTrace();}


    }
}

class Assistant extends SwingWorker<List<String>,String>
{

    double var =0.0;

    String lines;

    List<String> processing_output;

    InputStream inwards_pipe;

    InputStreamReader inwards_tap;

    BufferedReader huge_pipe;

    @Override
    protected List<String> doInBackground() throws Exception {

        Home.begin_full_flash=Home.full_flash_setup.start();

        inwards_pipe = Home.begin_full_flash.getInputStream();

        inwards_tap = new InputStreamReader(inwards_pipe);

        huge_pipe = new BufferedReader(inwards_tap);

        while((lines = huge_pipe.readLine())!=null)
        {

            publish(lines+"\n");
        }





        return null
                ;



    }


    @Override
    protected void process(List<String> chunks)
    {

        Home.output.appendText(chunks.toString());

        Home.progress.setProgress(var+=0.01);




    }

    @Override
    protected void done()
    {

        Home.progress.setEffect(new Glow(1));

        Home.progress.setProgress(1.0);

    }
}

class Recovery_flasher extends SwingWorker<Void,Void>
{

    Double var =0.0;

    @Override
    protected Void doInBackground() throws Exception {


        Home.flash_recovery_setup.start();
        publish();
        return null;

    }

    @Override

    protected void process(List<Void> chunks)
    {

        Home.partition_progress_bar.setProgress(var+0.2);



    }

    @Override
    protected void done()
    {

        Home.partition_progress_bar.setProgress(1);

    }
}