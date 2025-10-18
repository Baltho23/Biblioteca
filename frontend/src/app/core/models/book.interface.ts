export interface Book {
  id: number;
  title: string;
  author: string;
  genre: string;
  copiesAvailable: number;
}

export interface BookSaveDto {
  title: string;
  author: string;
  genre: string;
  copiesAvailable: number;
}

export interface BookStats {
  bookId: number;
  title: string;
  totalLoans: number;
}
