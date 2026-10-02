import {z} from "zod";

export interface User {
  loginId: string;
  name: string;
  email: string;
  language: string;
  timezone: string;
}

export const UserEntrySchema = z.object({
  loginId: z.string().nonempty().min(8).max(255).regex(/^\w{8,}$/),
  name: z.string().nonempty().max(255),
  email: z.email().max(255),
  length: z.string().nonempty().regex(/ja|en/),
  timezone: z.string().nonempty().max(255),
});

export type UserEntryParam = z.infer<typeof UserEntrySchema>;