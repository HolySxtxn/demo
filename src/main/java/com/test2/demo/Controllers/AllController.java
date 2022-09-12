package com.test2.demo.Controllers;

import com.test2.demo.Models.KindergartenTeacher;
import com.test2.demo.repo.KindergartenTeacherRepository;
import com.test2.demo.Models.Student;
import com.test2.demo.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AllController {

    @Autowired
    private KindergartenTeacherRepository KindergartenTeacherRepository;
    @Autowired
    private StudentRepository StudentRepository;


    @GetMapping("/")
    public String Main(Model model) {
        return "Main";
    }

    @GetMapping("/student")
    public String GetStudent(Model model) {
        Iterable<Student> student = StudentRepository.findAll();
        model.addAttribute("student", student);
        return "student-main";
    }

    @GetMapping("/kindergartenTeacher")
    public String GetKindergartenTeacher(Model model)
    {
        Iterable<KindergartenTeacher> KindergartenTeachers = KindergartenTeacherRepository.findAll();
        model.addAttribute("KindergartenTeachers",KindergartenTeachers);
        return "KindergartenTeacher-main";
    }


    @GetMapping("/student/add")
    public String studentAdd(Model model)
    {
        return "student-add";
    }
    @GetMapping("/KindergartenTeacher/add")
    public String KindergartenTeacherAdd(Model model)
    {
        return "KindergartenTeacher-add";
    }


    @PostMapping("/student/add")
    public String studentAdd(@RequestParam(value="name") String name,
                             @RequestParam(value ="surname") String surname,
                             @RequestParam(value = "patronymic") String patronymic,
                             @RequestParam(value = "age") Integer age,
                             @RequestParam(value = "groupp") String groupp, Model model)
    {
        Student student = new Student(name,surname,patronymic,age,groupp);
        StudentRepository.save(student);
        return "redirect:/student";
    }
    @PostMapping("/KindergartenTeacher/add")
    public String KindergartenTeacherAdd(@RequestParam(value="name") String name,
                            @RequestParam(value ="surname") String surname,
                            @RequestParam(value = "patronymic") String patronymic,
                            @RequestParam(value = "age") Integer age,
                            @RequestParam(value = "groupp") String groupp, Model model)
    {
        KindergartenTeacher KindergartenTeacher = new KindergartenTeacher(name,surname,patronymic,age,groupp);
        KindergartenTeacherRepository.save(KindergartenTeacher);
        return "redirect:/kindergartenTeacher";
    }



    @GetMapping("/student/filter")
    public String studentFilter(Model model)
    {
        return "student-filter";
    }
    @GetMapping("/KindergartenTeacher/filter")
    public String KindergartenTeacherFilter(Model model)
    {
        return "KindergartenTeacher-filter";
    }

    @PostMapping("/student/filter/resulttoch")
    public String studentResult(@RequestParam String surname, Model model)
    {
        List<Student> result = StudentRepository.findBySurname(surname);
        model.addAttribute("result", result);
        return "student-filter";
    }

    @PostMapping("/student/filter/resultnetochn")
    public String studentResultn(@RequestParam String surname, Model model)
    {
        List<Student> result = StudentRepository.findBySurnameContains(surname);
        model.addAttribute("result", result);
        return "student-filter";
    }

    @PostMapping("KindergartenTeacher/filter/resulttoch")
    public String KindergartenTeacherResult(@RequestParam String surname, Model model)
    {
        List<KindergartenTeacher> result = KindergartenTeacherRepository.findBySurname(surname);
        model.addAttribute("result", result);
       return "KindergartenTeacher-filter";
   }

    @PostMapping("KindergartenTeacher/filter/resultnetochn")
    public String KindergartenTeacherResult1(@RequestParam String surname, Model model)
    {
        List<KindergartenTeacher> result = KindergartenTeacherRepository.findBySurnameContains(surname);
        model.addAttribute("result", result);
        return "KindergartenTeacher-filter";
    }

//Третья практическая
@GetMapping("/KindergartenTeacher/{id}")
public String KindergartenTeacherDetails(@PathVariable(value = "id") long id, Model model)
{
    Optional<KindergartenTeacher> KindergartenTeacher = KindergartenTeacherRepository.findById(id);
    ArrayList<KindergartenTeacher> res = new ArrayList<>();
    KindergartenTeacher.ifPresent(res::add);

    model.addAttribute("KindergartenTeacher", res);
    if(!KindergartenTeacherRepository.existsById(id))
    {
        return "redirect:/KindergartenTeacher-main";
    }
    return "KindergartenTeacher-details";
}

    @GetMapping("/KindergartenTeacher/{id}/edit")
    public String KindergartenTeacherEdit(@PathVariable("id")long id, Model model) {
        if (!KindergartenTeacherRepository.existsById(id)) {
            return "redirect:/kindergartenTeacher";
        }
        Optional<KindergartenTeacher> KindergartenTeacher = KindergartenTeacherRepository.findById(id);
        ArrayList<KindergartenTeacher> res = new ArrayList<>();
        KindergartenTeacher.ifPresent(res::add);
        model.addAttribute("KindergartenTeacher", res);
        return "KindergartenTeacher-edit";
    }
    @PostMapping("/KindergartenTeacher/{id}/edit")
    public String KindergartenTeacherUpdate(@PathVariable("id")long id,
                                            @RequestParam String name,
                                            @RequestParam String surname,
                                            @RequestParam String patronymic,
                                            @RequestParam Integer age,
                                            @RequestParam String groupp,
                               Model model)
    {
        KindergartenTeacher KindergartenTeacher = KindergartenTeacherRepository.findById(id).orElseThrow();
        KindergartenTeacher.setName(name);
        KindergartenTeacher.setSurname(surname);
        KindergartenTeacher.setPatronymic(patronymic);
        KindergartenTeacher.setAge(age);
        KindergartenTeacher.setGroupp(groupp);
        KindergartenTeacherRepository.save(KindergartenTeacher);
        return "redirect:/kindergartenTeacher";
    }
    @PostMapping("/KindergartenTeacher/{id}/remove")
    public String KindergartenTeacherDelete(@PathVariable("id") long id, Model model){
        KindergartenTeacher KindergartenTeacher = KindergartenTeacherRepository.findById(id).orElseThrow();
        KindergartenTeacherRepository.delete(KindergartenTeacher);
        return "redirect:/kindergartenTeacher";
    }


    @GetMapping("/student/{id}")
    public String studentDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Student> student = StudentRepository.findById(id);
        ArrayList<Student> res = new ArrayList<>();
        student.ifPresent(res::add);

        model.addAttribute("student", res);
        if(!StudentRepository.existsById(id))
        {
            return "redirect:/students-main";
        }
        return "student-details";
    }
    
    @GetMapping("/student/{id}/edit")
    public String studentEdit(@PathVariable("id")long id, Model model)
    {
        if(!StudentRepository.existsById(id)){
            return "redirect:/student";
        }
        Optional<Student> student = StudentRepository.findById(id);
        ArrayList<Student> res = new ArrayList<>();
        student.ifPresent(res::add);
        model.addAttribute("student",res);
        return "student-edit";
    }
    @PostMapping("/student/{id}/edit")
    public String StudentUpdate(@PathVariable("id")long id,
                                @RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String patronymic,
                                @RequestParam Integer age,
                                @RequestParam String groupp,
                                Model model)
    {
        Student student = StudentRepository.findById(id).orElseThrow();
        student.setName(name);
        student.setSurname(surname);
        student.setPatronymic(patronymic);
        student.setAge(age);
        student.setGroupp(groupp);
        StudentRepository.save(student);
        return "redirect:/student";
    }
    @PostMapping("/student/{id}/remove")
    public String StudentDelete(@PathVariable("id") long id, Model model){
        Student student = StudentRepository.findById(id).orElseThrow();
        StudentRepository.delete(student);
        return "redirect:/student";
    }
}