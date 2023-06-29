import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        FileReader fRub = new FileReader("Rubles.txt");
        int c;
        String sRub = "";
        while ((c = fRub.read()) != -1) {
            sRub += (char) c;
        }
        System.out.println("1p in doll: " + sRub);
        double Rub = Double.parseDouble(sRub);
        FileReader fDoll = new FileReader("Dollars.txt");
        String sDoll = "";
        while ((c = fDoll.read()) != -1) {
            sDoll += (char) c;
        }
        System.out.println("$1 in rub: " + sDoll);
        double Doll = Double.parseDouble(sDoll);
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the example: ");//toDollars(737p+toRubles($85,4))
        String line1 = sc.nextLine();
        String line = "";
        for (int i = 0; i < line1.length(); i++) {
            if (line1.charAt(i) == ',') {
                line += ".";
                continue;
            }
            line += line1.charAt(i);
        }
        String sum = "";
        String toD = "toDollars", toR = "toRubles";
        for (int i = 0; i < line.length(); i++) {
            if (line.substring(i, i + 9).equals(toD)) {
                sum = "$" + convAll(line.substring(i + 9), Rub,1);
                break;
            }
            if (line.substring(i, i + 8).equals(toR)) {
                sum = convAll(line.substring(i + 8), Doll,2) + "p";
                break;
            }
        }
        System.out.println(sum);
    }

    public static double convAll(String s, Double val,int n) {
        String nums = "0123456789.+-p$";
        String op = "+-";
        String s2 = "";
        double sum = 0;
        List<String> list = new ArrayList<>(List.of(s.split("")));
        for (int i = 0; i < list.size(); i++) {
            if (!nums.contains(list.get(i))) {
                list.remove(i);
                i--;
            }
        }
        int zn = 1;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).equals("-")){
                zn=-1;
            }
            if(list.get(i).equals("+")){
                zn=1;
            }
            if(list.get(i).equals("p")){
                s2 = "";
                int a = i-1;
                while(a>=0){
                    if(!op.contains(list.get(a))){
                        s2=list.get(a)+s2;
                        a--;
                        continue;
                    }
                    break;
                }
                if(n==1){
                sum+=zn*Math.round(Double.parseDouble(s2)*val*100.0)/100.0;
                }else sum+=zn*Double.parseDouble(s2);
            }
            if(list.get(i).equals("$")){
                s2="";
                int a = i+1;
                while(a<list.size()){
                    if(!op.contains(list.get(a))){
                        s2+=list.get(a);
                        a++;
                        continue;
                    }
                    break;
                }
                if(n==2){
                    sum+=zn*Math.round(Double.parseDouble(s2)*val*100.0)/100.0;//toDollars(737p+toRubles($85,4))
                }else sum+=zn*Double.parseDouble(s2);//toRubles(737p+toDollars($85,4))

            }
        }
        return Math.round(sum*100)/100.0;
    }
}