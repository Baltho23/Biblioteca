import { environment } from "../../../environment";

const API_URL = environment.apiUrl;

export const Endpoints = {
  book: {
    getBook: (id: number) => `${API_URL}/books/${id}`,
    putBook: (id: number) => `${API_URL}/books/${id}`,
    deleteBook: (id: number) => `${API_URL}/books/${id}`,
    getBooks: () => `${API_URL}/books`,
    postBooks: () => `${API_URL}/books`,
  }
};
