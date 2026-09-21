export interface User {
  id: string;
  loginId: string;
  name: string;
  email: string;
  password: string;
  lockout: boolean;
  language: string;
  timezone: string;
  createdAt: Date;
  createdBy: string;
  updatedAt: Date;
  updatedBy: string;
}