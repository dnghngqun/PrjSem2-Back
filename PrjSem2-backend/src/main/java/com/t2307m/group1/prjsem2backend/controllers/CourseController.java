
package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.Course;
import com.t2307m.group1.prjsem2backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    // Ánh xạ các yêu cầu http GET với /api/courses
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
        // trả về danh sách khóa học với trạng thái ok(200)
    }

    @GetMapping("/{id}")
    //Ánh xạ
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        //@PathVariable lấy giá trị của {id} từ url và gán nó cho biến id
        Optional<Course> course = courseService.getCourseById(id);
        //gọi phương thức để lấy id khóa học
        return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                //nếu khóa học tồn tại ,trả về khóa học với trạng thái ok
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }//nếu ko tồn tại 404 not_found

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course newCourse = courseService.addCourse(course);
        // gọi method addCourse từ courseservice
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
        //trả về khóa học mới với mã trạng thái (201-created)
    }

    @PutMapping("/{id}")
    // ánh xạ các yêu cầu http put tới /api/courses/id
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course courseDetails) {
        //@PathVariable lấy giá trị  của {id} từ url, @RequestBody : lấy đối tượng course từ yêu cầu của http
        try {
            Course updatedCourse = courseService.updateCourse(id, courseDetails);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
            // cập nhật khóa học tc ->ok
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            //no-not-found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        try {
            courseService.deleteCourse(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            //xóa tc-no_content-204
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            failed - not_found
        }
    }

    // Các endpoint sử dụng Optional
    @GetMapping("/name/{name}")
    public ResponseEntity<Course> getCourseByName(@PathVariable String name) {
        Optional<Course> course = courseService.getCourseByName(name);
        return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<Course>> getCoursesByPrice(@PathVariable double price) {
        Optional<List<Course>> courses = courseService.getCoursesByPrice(price);
        return courses.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Course>> getCoursesByStatus(@PathVariable int status) {
        Optional<List<Course>> courses = courseService.getCoursesByStatus(status);
        return courses.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{name}/status/{status}")
    public ResponseEntity<List<Course>> getCoursesByNameAndStatus(@PathVariable String name, @PathVariable int status) {
        Optional<List<Course>> courses = courseService.getCoursesByNameAndStatus(name, status);
        return courses.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
