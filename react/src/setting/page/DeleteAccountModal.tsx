import React, { memo, useState } from 'react';
import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Button,
  Checkbox,
  FormControlLabel,
} from '@mui/material';

interface DeleteAccountModalProps {
  open: boolean;
  onClose: () => void;
  onConfirm: () => void;
}

const DeleteAccountModal: React.FC<DeleteAccountModalProps> = ({
  open,
  onClose,
  onConfirm,
}) => {
  const [isChecked, setIsChecked] = useState(false);

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setIsChecked(event.target.checked);
  };

  const handleConfirm = () => {
    onConfirm();
    setIsChecked(false);
    onClose();
  };

  return (
    <Dialog
      open={open}
      onClose={onClose}
      aria-labelledby='alert-dialog-title'
      aria-describedby='alert-dialog-description'>
      <DialogTitle id='alert-dialog-title'>회원 탈퇴</DialogTitle>
      <DialogContent>
        <DialogContentText id='alert-dialog-description'>
          정말로 탈퇴하시겠습니까? 탈퇴하시면 모든 데이터가 삭제됩니다.
        </DialogContentText>
        <FormControlLabel
          control={
            <Checkbox checked={isChecked} onChange={handleCheckboxChange} />
          }
          label='예, 탈퇴에 동의합니다.'
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>취소</Button>
        <Button onClick={handleConfirm} color='error' disabled={!isChecked}>
          확인
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default memo(DeleteAccountModal);
