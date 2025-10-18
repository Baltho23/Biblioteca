import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Member, MemberSaveDto } from '../models/member.interface';
import { Observable } from 'rxjs';
import { Endpoints } from '../constants/endpoints';

@Injectable({
  providedIn: 'root'
})
export class MemberService {
  constructor(private http: HttpClient) {}

  getMembers(): Observable<Member[]> {
    return this.http.get<Member[]>(Endpoints.member.getMembers());
  }

  addMember(membersave: MemberSaveDto): Observable<Member> {
    return this.http.post<Member>(Endpoints.member.postMembers(), membersave);
  }

  updateMember(id: number, memberEdit: MemberSaveDto): Observable<Member> {
    return this.http.put<Member>(Endpoints.member.putMember(id), memberEdit);
  }

  deleteMember(id: number): Observable<Boolean> {
    return this.http.delete<Boolean>(Endpoints.member.deleteMember(id));
  }

}
