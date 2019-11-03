import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        Scanner read = new Scanner(System.in);
        String caracteres = "#@_\\/.*\",“”";
        String value;
        ClassSchedule cs = new ClassSchedule();

        ArrayList<Professor> professor = new ArrayList<Professor>();
        ArrayList<Subject> subject = new ArrayList<Subject>();
        ArrayList<Classroom> classroom = new ArrayList<Classroom>();
        ArrayList<ClassSchedule> classSchedule = new ArrayList<ClassSchedule>();
        ArrayList<String> exception = new ArrayList<>();


        // Register Professor
        value = read.nextLine();
        while (!value.equals("")) {
            String value1 = value.replaceAll(", ",",");
            String[] info = value1.split("[" + Pattern.quote(caracteres) + "]+");
            String nome = info[1];
            String id = info[2];
            professor.add(new Professor(nome, id));
            value = read.nextLine();
        }
        

        // Register Subject
        value = read.nextLine();
        while (!value.equals("")) {
            String value1 = value.replaceAll(", ",",");
            String[] info = value1.split("[" + Pattern.quote(caracteres) + "]+");
            String nome = info[1];
            String id = info[2];
            subject.add(new Subject(nome, id));
            value = read.nextLine();
        }
        

        // Register Classroom
        value = read.nextLine();
        while (!value.equals("")) {
            String value1 = value.replaceAll(", ",",");
            String[] info = value1.split("[" + Pattern.quote(caracteres) + "]+");
            String nome = info[1];
            String id = info[2];
            classroom.add(new Classroom(nome, id));
            value = read.nextLine();
        }
        
        //Make ClassSchedule
        value = read.nextLine();
        while (!value.equals("")) {
            String value1 = value.replaceAll(", ",",");
            String[] info = value1.split("[" + Pattern.quote(caracteres) + "]+");
            String subject_id = info[1];
            String day = info[2];
            String hour = info[3];
            String duration = info[4];
            String classroom_id = info[5];
            boolean valid_time = true;

            Subject sb = new Subject(subject_id);
            if(!subject.contains(sb)){
                exception.add("Turma " + subject_id + " inexistente.");
                valid_time = false;
            }

            Classroom ia = new Classroom(classroom_id);
            if(!classroom.contains(ia)){
                exception.add("Sala ou Laboratório " + classroom_id + " inexistente.");
                               valid_time = false;
            }


            if (!cs.verifyHour(hour)) {
                exception.add("Horário inicial " + hour + " de aula não permitido");
//                valid_time = false;
            }

//            if (!cs.verifyDay(day)) {
//                exception.add("Dia " + day + " não existe!");
////                valid_time = false;
//            }


            if (!cs.verifyDuration(hour, duration)) {
                exception.add("Duração de aula de " + subject_id + " ultrapassa os limites permitidos");
//                valid_time = false;
            }

            if(!cs.verifyInteception(classSchedule, hour, duration, day, subject_id, classroom_id, exception)){
//                valid_time = false;
            }


            if(valid_time){
                classSchedule.add(new ClassSchedule(subject_id, day, hour, duration, classroom_id));
            }

            value = read.nextLine();
        }

        // Professor X Subject


        value = read.nextLine();
        while (!value.equals("")) {
            String value1 = value.replaceAll(", ",",");
            String[] info = value1.split("[" + Pattern.quote(caracteres) + "]+");
            String sub = info[1];
            String prof = info[2];
            boolean valid_time = true;

            Subject sb = new Subject(sub);
            if(!subject.contains(sb)){
                exception.add(sub + " é uma turma inexistente.");
                valid_time = false;
            }

            Professor pf = new Professor(prof);
            if(!professor.contains(pf)){
                exception.add("Professor " + prof + " inexistente.");
                valid_time = false;
            }

            if (valid_time){
                cs.verifyProfessor(prof,sub,classSchedule,exception);
            }

            value = read.nextLine();
        }

        System.out.println("SCHEDULE");
        for (ClassSchedule p : classSchedule) {
            System.out.print(p.getSubject_id() + " " + p.getDay() + " " + p.getTime() + " " + p.getDuration() + " "
                    + p.getClassroom_id());
        if(p.getProfessor_id()!=null) System.out.print(" "+p.getProfessor_id());
            System.out.println();
        }
        System.out.println();
        System.out.println("EXCEPTIONS");
        for (String p : exception) {
            System.out.println(p);
        }

    }
}
