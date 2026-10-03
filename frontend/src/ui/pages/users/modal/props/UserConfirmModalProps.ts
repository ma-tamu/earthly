import type {UserEntryParam} from "../../../../../types/user.ts";

export type UserConfirmModalProps = {
  isOpen: boolean;
  onClose: () => void;
  onConfirm: () => void;
  formData: UserEntryParam
  isSubmitting: boolean;
};