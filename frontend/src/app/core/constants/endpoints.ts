import { environment } from "../../../environment";

const API_URL = environment.apiUrl;

export const Endpoints = {
  book: {
    getBook: (id: number) => `${API_URL}/books/${id}`,
    putBook: (id: number) => `${API_URL}/books/${id}`,
    deleteBook: (id: number) => `${API_URL}/books/${id}`,
    getBooks: () => `${API_URL}/books`,
    postBooks: () => `${API_URL}/books`,
  },
  member: {
    getMember: (id: number) => `${API_URL}/members/${id}`,
    putMember: (id: number) => `${API_URL}/members/${id}`,
    deleteMember: (id: number) => `${API_URL}/members/${id}`,
    getMembers: () => `${API_URL}/members`,
    postMembers: () => `${API_URL}/members`,
  },
  loan: {
    getLoan: (id: number) => `${API_URL}/loans/${id}`,
    putLoan: (id: number) => `${API_URL}/loans/${id}`,
    deleteLoan: (id: number) => `${API_URL}/loans/${id}`,
    getLoans: () => `${API_URL}/loans`,
    postLoans: () => `${API_URL}/loans`,
  }
};
