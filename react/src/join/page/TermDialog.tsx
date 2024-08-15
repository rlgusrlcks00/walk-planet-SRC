import React, { memo, useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Checkbox,
  FormControlLabel,
  Typography,
} from '@mui/material';
import TermComponenet from '@components/TermComponenet';

interface TermDialogProps {
  open: boolean;
  onClose: (agreed: boolean) => void;
}

const TermDialog: React.FC<TermDialogProps> = ({ open, onClose }) => {
  const [agreed, setAgreed] = useState(false);

  const handleAgreeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setAgreed(event.target.checked);
  };

  const handleAgree = () => {
    onClose(true);
  };

  const handleClose = () => {
    onClose(false);
    setAgreed(false);
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>이용 약관</DialogTitle>
      <DialogContent dividers>
        <TermComponenet />
      </DialogContent>
      <DialogActions>
        <FormControlLabel
          control={<Checkbox checked={agreed} onChange={handleAgreeChange} />}
          label='약관에 동의합니다'
        />
        <Button onClick={handleClose}>취소</Button>
        <Button onClick={handleAgree} disabled={!agreed}>
          동의
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default memo(TermDialog);
