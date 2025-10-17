package com.biblioteca.backend.service;

import com.biblioteca.backend.dto.MemberSaveDto;
import com.biblioteca.backend.entity.Member;
import com.biblioteca.backend.repository.MemberRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Miembro no encontrado con id: " + id));
    }

    public Member create(MemberSaveDto memberSaveDto) {
        if (memberRepository.existsByEmail(memberSaveDto.email())){
            throw new EntityExistsException("El email existe en la base de datos");
        }
        Member member = Member.builder()
                .email(memberSaveDto.email())
                .name(memberSaveDto.name())
                .build();
        return memberRepository.save(member);
    }

    public Member update(Long id, MemberSaveDto memberSaveDto) {
        Member memberFind = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Member"));

        if (!memberFind.getEmail().equals(memberSaveDto.email()) &&
                memberRepository.existsByEmail(memberSaveDto.email())) {
            throw new DataIntegrityViolationException("El correo electrónico ya está en uso por otro miembro");
        }

        memberFind.setName(memberSaveDto.name());
        memberFind.setEmail(memberSaveDto.email());

        return memberRepository.save(memberFind);
    }

    public Boolean delete(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
