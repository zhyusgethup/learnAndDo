package copyToFile.analyse.template.simple;
import java.util.*;
import java.io.*;
public class ScannerTest {
    public static void main(String[] args) throws IOException{//这里涉及到文件io操作
        double sum=0.0;
        int count=0;
        FileWriter fout=new FileWriter("text.txt");
        fout.write("2 2.2 3 3.3 4 4.5 done");//往文件里写入这一字符串
        fout.close();
        FileReader fin=new FileReader("text.txt");
        Scanner scanner=new Scanner(fin);//注意这里的参数是FileReader类型的fin
        while(scanner.hasNext()){//如果有内容
            if(scanner.hasNextDouble()){//如果是数字
                sum=sum+scanner.nextDouble();
                count++;
            }else{
                String str=scanner.next();
                if(str.equals("done")){
                    break; 
                }else{
                    System.out.println("文件格式错误!");
                    return;
                }
            }
        }
        fin.close();
        System.out.println("文件中数据的平均数是:"+sum/count);
    }
}