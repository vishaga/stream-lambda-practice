package com.vishaga.model;

public record University(String name, String country, String establishedYear, String numOfFaculty,
                         String numOfStaff, String	numOfDoctoral, String numOfPostGrad, String numOfUnderGrad,
                         String	numOfStudent) {


    @Override
    public String toString() {

        //return establishedYear + '\t' ;
        return      name + '\t' +
                    country + '\t' +
                    establishedYear + '\t' +
                numOfFaculty + '\t' +
                numOfStaff + '\t' +
                numOfDoctoral + '\t' +
                numOfPostGrad + '\t' +
                numOfUnderGrad + '\t' +
                numOfStudent ;
    }

    public static University from(String line){

        String[] strings = line.split("\t");
        String name = strings[0].trim();
        String country = strings[1].trim();
        String establishedYear = "";
        try{
            int i=0;
            while(!Character.isDigit(strings[2].trim().charAt(i))) {
                //System.out.println("strings[2] = " + strings[2]);
                i++;
            }
            //System.out.println("F: " + strings[2].trim().substring(i,4));
            establishedYear = String.valueOf(Integer.parseInt(strings[2].trim().substring(i,4)));
        }catch (Exception ex){

            establishedYear = strings[2].trim();
            //System.out.println("Failed: " + establishedYear + ", strings: "+line);
        }

        String numOfFaculty = strings[3].trim();
        String numOfStaff = isValid(strings[4].trim());
        String numOfDoctoral = strings[5].trim();
        String numOfPostGrad = strings[6].trim();
        String numOfUnderGrad = strings[7].trim();;
        String numOfStudent = strings[8].trim();
        return new University(name,country,establishedYear,numOfFaculty,numOfStaff,numOfDoctoral,numOfPostGrad,numOfUnderGrad,numOfStudent);
    }

    private static String isValid(String value){
        try{
            return String.valueOf(Integer.parseInt(value.replace(",","").replace(".0","").trim()));

        }catch (Exception ex){
            System.out.println(ex);
            return value;
        }
    }
}
