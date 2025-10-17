package com.biblioteca.backend.controller;

import com.biblioteca.backend.dto.MemberSaveDto;
import com.biblioteca.backend.entity.Member;
import com.biblioteca.backend.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAll() {
        List<Member> members = memberService.findAll();

        if (members.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public Member getById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Member> create(@Valid @RequestBody MemberSaveDto memberSaveDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.create(memberSaveDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> update(@PathVariable Long id, @Valid @RequestBody MemberSaveDto memberSaveDto) {
        return ResponseEntity.ok(memberService.update(id, memberSaveDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        Boolean deleted = memberService.delete(id);

        if (deleted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }
}