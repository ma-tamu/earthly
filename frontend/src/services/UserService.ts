export type User = {
  id: string;
  name: string;
  email: string;
  role: 'admin' | 'user';
  status: 'active' | 'inactive';
  createdAt: string;
};

export type UserPaginatedResponse = {
  data: User[];
  meta: {
    currentPage: number;
    totalPages: number;
    totalCount: number;
  };
};

export interface UserService {
  getUsers(page: number): Promise<UserPaginatedResponse>;
}