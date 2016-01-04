package nsl;

import java.io.IOException;

/**
 *
 * @author Stuart
 */
public class Main
{
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws IOException
  {
    String scriptPath = null;
    boolean noPauseOnError = false;
    boolean noMakeNSIS = false;
    String nsisPath = "..\\makensisw.exe";
    String output = "./";

    for (String arg : args)
    {
      if (arg.equalsIgnoreCase("/nomake") || arg.equalsIgnoreCase("--nomake"))
      {
        noMakeNSIS = true;
      }
      else if (arg.equalsIgnoreCase("/nopause") || arg.equalsIgnoreCase("--nopause"))
      {
        noPauseOnError = true;
      }
      else if (arg.startsWith("/nsisPath") || arg.startsWith("--nsisPath"))
      {
        nsisPath = arg.split("=")[1];
      }
      else if (arg.startsWith("/output") || arg.startsWith("--output"))
      {
        output = arg.split("=")[1];
      }
      else
      {
        scriptPath = arg.trim();
      }
    }

    if (scriptPath == null || scriptPath.isEmpty())
      showUsage();

    System.exit(ScriptParser.parse(scriptPath, output, noPauseOnError, noMakeNSIS, nsisPath));
  }

  /**
   * Shows command line usage for the nsL assembler before exiting.
   */
  private static void showUsage()
  {
    System.out.println("Usage:");
    System.out.println("  java -jar nsL.jar [Options] script.nsl");
    System.out.println();
    System.out.println("Options:");
    System.out.println("  -n");
    System.exit(1);
  }
}
