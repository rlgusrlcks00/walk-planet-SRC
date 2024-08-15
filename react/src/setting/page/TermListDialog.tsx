import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from '@mui/material';
import TermComponenet from '@components/TermComponenet';
import React, { memo } from 'react';

interface TermListDialogProps {
  open: boolean;
  onClose: () => void;
}

const TermListDialog: React.FC<TermListDialogProps> = ({ open, onClose }) => {
  return (
    <>
      <Dialog open={open} onClose={onClose}>
        <DialogContent>
          <TermComponenet />
        </DialogContent>
        <DialogActions>
          <Button onClick={onClose}>닫기</Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default memo(TermListDialog);
