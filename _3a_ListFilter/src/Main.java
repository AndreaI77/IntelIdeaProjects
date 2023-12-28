import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static List<Student> filterStudents(List<Student> srcList, Predicate<Student> predicate){

        List<Student> lista = new ArrayList<>();
        for( Student st : srcList){
            if( predicate.test(st)){
                lista.add(st);
            }
        }
        return lista;
    }
    public static List<Student> getOldestNames (List<Student> list){
        return list.stream().sorted(Comparator.comparingInt(Student::getAge)).limit(3).collect(Collectors.toList());
    }
    public static Set<String> getAllSubjects (List<Student> list){


        List<String> subjects = list.stream().map(Student::getSubjects).reduce(new ArrayList<String>() ,(list1, list2) -> {
                    List<String> l = new ArrayList<>(list1);
                    l.addAll(list2);
                    return l;
                });
        return new TreeSet<>(subjects);
    }

    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student("Peter Pan", 23, Arrays.asList("Math","English","Programming","Philosophy")));
        list.add(new Student("Mary Lawson", 17, Arrays.asList("Physics","Spanish","Programming")));
        list.add(new Student("Sue Smith", 20, Arrays.asList("English","Literature","Philosophy")));
        list.add(new Student("John Peterson", 34, Arrays.asList("Math","Physics","Drawing","Chemistry")));
        list.add(new Student("Trevor Swanson", 19, Arrays.asList("English","Spanish","Drawing")));
        list.add(new Student("Jane Traviata", 15, Arrays.asList("Programming","Philosophy")));
        list.add(new Student("Boris Smirnoff", 26, Arrays.asList("Spanish","English","Music","Philosophy")));
        list.add(new Student("Laurent Garnier", 23, Arrays.asList("Math","Programming","Philosophy")));


        List<Student> older20 = filterStudents(list, st -> st.getAge() >= 20);
        System.out.println("Older than 20:\n" + older20);

        List<Student> prog = filterStudents(list, st -> st.getSubjects().contains("Programming"));
        System.out.println("Inscribed in programming:\n" + prog);

        List<Student> peter = filterStudents(list, st -> st.getName().toUpperCase().contains("PETER"));
        System.out.println("Name contains Peter:\n" + peter);

        System.out.println("The three oldest ones: ");
        for(Student s : getOldestNames(list)){
            System.out.println(s);
        }

        System.out.println("All subjects with students: "+getAllSubjects(list));
        //************************************************************
        // Before Java 7
        class TestPredicate implements Predicate<Student>{
            @Override
            public boolean test(Student s) {
                return s.getAge() >= 20;
            }
        }

        TestPredicate tp = new TestPredicate();
        List<Student> older20z = filterStudents(list, tp);
        System.out.println("Older than 20:\n" + older20z);

        // Java 7
        List<Student> older20x = filterStudents(list, new Predicate<Student>() {
            @Override
            public boolean test(Student student) {
                return student.getAge() >= 20;
            }
        });
        System.out.println("Older than 20:\n" + older20x);

        // Java 8 Lambda
        List<Student> older20xx = filterStudents(list, st -> st.getAge() >= 20);
        System.out.println("Older than 20:\n" + older20xx);

    }
}