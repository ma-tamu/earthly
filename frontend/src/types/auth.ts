import {z} from "zod";

export const loginSchema = z.object({
  loginId: z.string().nonempty(),
  password: z.string().nonempty(),
})

// 💡 Zodからログインの厳格な入力型（LoginParams）を自動抽出
export type LoginParams = z.infer<typeof loginSchema>;