//package org.example;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.javafaker.Faker;
//import com.vishaga.model.Employee;
//import com.vishaga.model.Employee1;
//import com.vishaga.model.Position;
//import com.vishaga.utils.DataLoaderUtils;
//import com.vishaga.utils.WriterUtil;
//
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
//public class Main {
//
//    static Random rand = new Random();
//    static Faker faker = new Faker();
//    public static void main(String[] args) throws Exception{
//        //pullEmployee();
//        String string = faker.job().keySkills();
//        Set<String> skills = new HashSet<>();
//        for(int i=0;i<1000;i++){
//            skills.add(faker.job().keySkills());
//        }
//        System.out.println("string = " + skills);
//        List<Employee> employees = DataLoaderUtils.loadEmployee(10000);
//        Main m = new Main();
//        List<Employee> doj = m.doj(employees);
//        WriterUtil.write(doj, "sample_file.txt");
//        System.out.println("done = ");
//
//    }
//
//    private List<Employee> doj(List<Employee> employees){
//        return employees.stream()
//                //.map(this::newEmployee)
//                .toList();
//    }
//
//    private Employee newEmployee(Employee emp){
//        int age = emp.age();
//        LocalDate dob = emp.dob();
//        LocalDate doj = LocalDate.now();
//        List<String> skills = List.of();
//        if(emp.position() == Position.INTERN){
//            age = rand.nextInt(22,26);
//            dob = LocalDate.of(2024-age, dob.getMonth(),dob.getDayOfMonth());
//            doj = doj.minusDays(rand.nextInt(90,240));
//            skills = tSkills();
//            //System.out.println("tSkills = " + skills);
//
//        }else if(emp.position() == Position.TRAINEE){
//            age = rand.nextInt(24,27);
//            dob = LocalDate.of(2024-age, dob.getMonth(),dob.getDayOfMonth());
//            doj = doj.minusDays(rand.nextInt(185,400));
//            skills = tSkills();
//
//        }else if(emp.position() == Position.SE){
//            age = rand.nextInt(25,29);
//            dob = LocalDate.of(2024-age, dob.getMonth(),dob.getDayOfMonth());
//            doj = doj.minusDays(rand.nextInt(365,1200));
//            skills = t2Skills();
//
//        }else if(emp.position() == Position.SSE){
//            age = rand.nextInt(27,35);
//            dob = LocalDate.of(2024-age, dob.getMonth(),dob.getDayOfMonth());
//            doj = doj.minusDays(rand.nextInt(365,1200));
//            skills = t2Skills();
//
//        }else if(emp.position() == Position.CONSULTANT){
//            age = rand.nextInt(28,36);
//            dob = LocalDate.of(2024-age, dob.getMonth(),dob.getDayOfMonth());
//            doj = doj.minusDays(rand.nextInt(365,1200));
//            skills = t2Skills();
//
//        }else if(emp.position() == Position.SENIOR_CONSULTANT){
//            age = rand.nextInt(29,36);
//            dob = LocalDate.of(2024-age, dob.getMonth(),dob.getDayOfMonth());
//            doj = doj.minusDays(rand.nextInt(365,1200));
//            skills = t2Skills();
//
//        }else if(emp.position() == Position.LEAD){
//            age = rand.nextInt(33,38);
//            dob = LocalDate.of(2024-age, dob.getMonth(),dob.getDayOfMonth());
//            doj = doj.minusDays(rand.nextInt(500,1500));
//            skills = t2Skills();
//        }else if(emp.position() == Position.SENIOR_LEAD){
//            age = rand.nextInt(35,40);
//            dob = LocalDate.of(2024-age, dob.getMonth(),dob.getDayOfMonth());
//            doj = doj.minusDays(rand.nextInt(800,1800));
//            skills = t2Skills();
//        }else if(emp.position() == Position.MANAGER){
//            age = rand.nextInt(38,42);
//            dob = LocalDate.of(2024-age, dob.getMonth(),dob.getDayOfMonth());
//            doj = doj.minusDays(rand.nextInt(800,1800));
//            skills = t2Skills();
//        }else if(emp.position() == Position.SENIOR_MANAGER){
//            age = rand.nextInt(40,50);
//            dob = LocalDate.of(2024-age, dob.getMonth(),dob.getDayOfMonth());
//            doj = doj.minusDays(rand.nextInt(100,1800));
//            skills = t3Skills();
//        }else if(emp.position() == Position.DIRECTOR){
//            age = rand.nextInt(46,58);
//            dob = LocalDate.of(2024-age, dob.getMonth(),dob.getDayOfMonth());
//            doj = doj.minusDays(rand.nextInt(700,2900));
//            skills = t4Skills();
//        }
//
////        int factor = (emp.age() - 22) == 0 ? rand.nextInt(20, 365): 365*(emp.age() - 22);
////        Date dateToConvert = faker.date().past(factor, TimeUnit.DAYS);
////        LocalDate doj = dateToConvert.toInstant()
////                .atZone(ZoneId.systemDefault())
////                .toLocalDate();
////        //System.out.println(doj);
//
//
//        LocalDate dOfJoining = LocalDate.now();
//
////        if(a > 72){
////            a = rand.nextInt(45, 72);
////            dob = LocalDate.of(2024-a, dob.getMonth(),dob.getDayOfMonth());
////        }
//        return new Employee(
//                emp.id(),
//                emp.firstName(),
//                emp.lastName(),
//                emp.emails(),
//                emp.contactNumber(),
//                age,
//                dob,
//                emp.salary(),
//                emp.address(),
//                emp.company(),
//                emp.sex(),
//                emp.maritalStatus(),
//                emp.race(),
//                emp.position(),
//                doj,
//                skills
//        );
//    }
//
//    private static void pullEmployee() throws Exception{
//        HttpClient build = HttpClient.newBuilder().build();
//
//        HttpRequest r = HttpRequest.newBuilder()
//                //.uri(URI.create("https://hub.dummyapis.com/employee?noofRecords=1000&idStarts=29001"))
//                .GET()
//                .build();
//
//        HttpResponse<String> str = build.send(r, HttpResponse.BodyHandlers.ofString());
//
//        ObjectMapper mapper = new ObjectMapper();
//        Employee1[] employee = mapper.readValue(str.body(), Employee1[].class);
//        Employee emp = Employee.from(employee[0].toString());
//        WriterUtil.write(Arrays.asList(employee), "sample_file.txt");
//    }
//
//    private List<String> tSkills(){
//        System.out.println(" = ");
//        int tmax = rand.nextInt(3, 5);
//        List<String> tSkills = this.tSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).toList();
//
//        int dmax = rand.nextInt(2, 4);
//        List<String> dbSkills = this.dbSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).toList();
//
//        HashSet<String> set = new HashSet<>();
//        while(tmax >= 0){
//            int r = rand.nextInt(0, 5);
//            String skill = tSkills.get(r);
//            if(set.add(skill)){
//                tmax--;
//            }
//        }
//        while(dmax >= 0){
//            int r = rand.nextInt(0, 4);
//            String skill = dbSkills.get(r);
//            if(set.add(skill)){
//                dmax--;
//            }
//        }
//        return new ArrayList<>(set);
//    }
//
//    private List<String> t2Skills(){
//        int tmax = rand.nextInt(7, 14);
//        List<String> tSkills = this.tSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int dmax = rand.nextInt(4, 5);
//        List<String> dbSkills = this.dbSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int cmax = rand.nextInt(3, 6);
//        List<String> cSkills = this.cSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int amax = rand.nextInt(2, 4);
//        List<String> aSkills = this.aSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int devmax = rand.nextInt(2, 4);
//        List<String> devopsSkills = this.devopsSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        HashSet<String> set = new HashSet<>();
//        while(tmax >= 0){
//            int r = rand.nextInt(0, 14);
//            String skill = tSkills.get(r);
//            if(set.add(skill)){
//                tmax--;
//            }
//        }
//        while(dmax >= 0){
//            int r = rand.nextInt(0, 6);
//            String skill = dbSkills.get(r);
//            if(set.add(skill)){
//                dmax--;
//            }
//        }
//        while(cmax >= 0){
//            int r = rand.nextInt(0, 7);
//            String skill = cSkills.get(r);
//            if(set.add(skill)){
//                cmax--;
//            }
//        }
//        while(amax >= 0){
//            int r = rand.nextInt(0, 4);
//            String skill = aSkills.get(r);
//            if(set.add(skill)){
//                amax--;
//            }
//        }
//        while(devmax >= 0){
//            int r = rand.nextInt(0, 5);
//            String skill = devopsSkills.get(r);
//            if(set.add(skill)){
//                devmax--;
//            }
//        }
//        return new ArrayList<>(set);
//    }
//
//    private List<String> t3Skills(){
//        System.out.println(" === ");
//
//        int tmax = rand.nextInt(7, 14);
//        List<String> tSkills = this.tSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int dmax = rand.nextInt(4, 6);
//        List<String> dbSkills = this.dbSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int cmax = rand.nextInt(3, 6);
//        List<String> cSkills = this.cSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int amax = rand.nextInt(2, 4);
//        List<String> aSkills = this.aSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int devmax = rand.nextInt(2, 5);
//        List<String> devopsSkills = this.devopsSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int mgrmax = rand.nextInt(2, 5);
//        List<String> mgrkills = this.mgrkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        HashSet<String> set = new HashSet<>();
//        while(tmax >= 0){
//            int r = rand.nextInt(0, 14);
//            String skill = tSkills.get(r);
//            if(set.add(skill)){
//                tmax--;
//            }
//        }
//        while(dmax >= 0){
//            int r = rand.nextInt(0, 6);
//            String skill = dbSkills.get(r);
//            if(set.add(skill)){
//                dmax--;
//            }
//        }
//        while(cmax >= 0){
//            int r = rand.nextInt(0, 7);
//            String skill = cSkills.get(r);
//            if(set.add(skill)){
//                cmax--;
//            }
//        }
//        while(amax >= 0){
//            int r = rand.nextInt(0, 4);
//            String skill = aSkills.get(r);
//            if(set.add(skill)){
//                amax--;
//            }
//        }
//        while(devmax >= 0){
//            int r = rand.nextInt(0, 6);
//            String skill = devopsSkills.get(r);
//            if(set.add(skill)){
//                devmax--;
//            }
//        }
//        while(mgrmax >= 0){
//            int r = rand.nextInt(0, 5);
//            String skill = mgrkills.get(r);
//            if(set.add(skill)){
//                mgrmax--;
//            }
//        }
//        return new ArrayList<>(set);
//    }
//
//    private List<String> t4Skills(){
//        System.out.println(" ==))) ");
//
//        int tmax = rand.nextInt(7, 14);
//        List<String> tSkills = this.tSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int dmax = rand.nextInt(4, 6);
//        List<String> dbSkills = this.dbSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int cmax = rand.nextInt(3, 6);
//        List<String> cSkills = this.cSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int amax = rand.nextInt(2, 4);
//        List<String> aSkills = this.aSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int devmax = rand.nextInt(2, 4);
//        List<String> devopsSkills = this.devopsSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int mgrmax = rand.nextInt(2, 5);
//        List<String> mgrkills = this.mgrkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        int dirmax = rand.nextInt(2, 3);
//        List<String> directorSkills = this.directorSkills.stream().flatMap(str -> Arrays.stream(str.split(","))).map(String::trim).toList();
//
//        HashSet<String> set = new HashSet<>();
//        while(tmax >= 0){
//            int r = rand.nextInt(0, 14);
//            String skill = tSkills.get(r);
//            if(set.add(skill)){
//                tmax--;
//            }
//        }
//        while(dmax >= 0){
//            int r = rand.nextInt(0, 6);
//            String skill = dbSkills.get(r);
//            if(set.add(skill)){
//                dmax--;
//            }
//        }
//        while(cmax >= 0){
//            int r = rand.nextInt(0, 6);
//            String skill = cSkills.get(r);
//            if(set.add(skill)){
//                cmax--;
//            }
//        }
//
//        while(amax >= 0){
//            int r = rand.nextInt(0, 4);
//            String skill = aSkills.get(r);
//            if(set.add(skill)){
//                amax--;
//            }
//        }
//        System.out.println(" ==))) ++");
//        while(devmax >= 0){
//            int r = rand.nextInt(0, 5);
//            String skill = devopsSkills.get(r);
//            if(set.add(skill)){
//                devmax--;
//            }
//        }
//        while(mgrmax >= 0){
//            int r = rand.nextInt(0, 5);
//            String skill = mgrkills.get(r);
//            if(set.add(skill)){
//                mgrmax--;
//            }
//        }
//        while(dirmax >= 0){
//            int r = rand.nextInt(0, 3);
//            String skill = directorSkills.get(r);
//            if(set.add(skill)){
//                dirmax--;
//            }
//        }
//        return new ArrayList<>(set);
//    }
//
//
//
//    List<String> tSkills = List.of("JavaScript,Python,Java,C#,TypeScript,Ruby,Go,Shell Scripting,C++,Swift,Kotlin,Objective-C,R,PowerShell,Bash,Haskell,Scala,Flutter,Dart");
//    List<String> dbSkills = List.of("SQL,Oracle,MySQL,MongoDB,NoSQL,DynamoDB,Aerospike");
//    List<String> cSkills = List.of("Cloud,AWS,GCP,Azure,Kubernetes,Docker,Terraform");
//    List<String> aSkills = List.of("Agile,Scrum,Kanban,Project Management");
//    List<String> devopsSkills = List.of("DevOps,Continuous Integration (CI), Continuous Deployment (CD),Networking,Security,CISSP (Certified Information Systems Security Professional)");
//    List<String> directorSkills = List.of("Leadership,Team Management,Strategic Planning");
//
//    List<String> mgrkills = List.of("Data Analysis,Business Intelligence,Business Analysis, Requirements Gathering, Process Modeling");
//}