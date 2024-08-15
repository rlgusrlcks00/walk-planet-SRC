import React, { memo } from 'react';
import { Modal, Box, Typography, Button } from '@mui/material';
import { useQuery } from 'react-query';
import { getVersion } from '@setting/api/SettingApi';

interface VersionModalProps {
  open: boolean;
  onClose: () => void;
  version: number;
  buildNumber: number;
}

const VersionModal = ({
  open,
  onClose,
  version,
  buildNumber,
}: VersionModalProps) => {
  const { data: recentVersion, isLoading: recentVersionLoading } = useQuery(
    'recnetVersion',
    getVersion,
  );
  return (
    <Modal open={open} onClose={onClose}>
      <Box
        sx={{
          position: 'absolute' as const,
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 300, // 더 작게 설정
          bgcolor: 'background.paper',
          borderRadius: 2, // 모서리를 둥글게
          boxShadow: 24,
          p: 3,
          outline: 'none', // 포커스 시 외곽선 제거
        }}>
        <Box
          display={'flex'}
          alignItems={'center'}
          justifyContent={'center'}
          flexDirection={'column'}>
          <img
            src={`${process.env.PUBLIC_URL}/WALKMATE_LOGO_TOP3.png`}
            alt='WalkMate Logo'
            style={{ maxWidth: '100%', borderRadius: '8px' }}
          />
          <Typography variant='caption' component='p'>
            현재 버전 : v{version}.{buildNumber}
          </Typography>
          <Typography variant='caption' component='p'>
            최신 버전 : v{recentVersion?.result.version.toFixed(1)}.
            {recentVersion?.result.buildNumber}
          </Typography>
        </Box>
        <Button
          variant='contained'
          color='primary'
          onClick={onClose}
          sx={{ mt: 2, width: '100%' }}>
          닫기
        </Button>
      </Box>
    </Modal>
  );
};

export default memo(VersionModal);
