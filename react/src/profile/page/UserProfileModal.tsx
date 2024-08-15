import React, { memo, useEffect, useState } from 'react';
import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
  Grid,
  RadioGroup,
  FormControlLabel,
  Radio,
} from '@mui/material';
import {
  UserProfileRequest,
  UserProfileResponse,
} from '@profile/types/UserProfileTypes';
import { userProfileUpdateInitial } from '@profile/types/UserProfileInitial';
import loadImage from 'blueimp-load-image'; // 이 라인을 추가하여 loadImage를 가져옵니다.

interface UserProfileModalProps {
  open: boolean;
  handleClose: () => void;
  handleSave: (profileData: UserProfileRequest) => void;
  initialData: UserProfileResponse;
}

const UserProfileModal: React.FC<UserProfileModalProps> = ({
  open,
  handleClose,
  handleSave,
  initialData,
}) => {
  const [profileData, setProfileData] =
    useState<UserProfileResponse>(initialData);
  const [updatedData, setUpdatedData] = useState<UserProfileRequest>(
    userProfileUpdateInitial,
  );
  const [preview, setPreview] = useState<string | null>(null);
  const [selectedImage, setSelectedImage] = useState<File | null>(null);
  const handleSetInitialData = () => {
    setProfileData(initialData);
    setUpdatedData({
      birth: initialData.result.birth,
      gender: initialData.result.gender,
      nickname: initialData.result.nickname,
      height: initialData.result.height,
      weight: initialData.result.weight,
      weightGoals: initialData.result.weightGoals,
      profileImg: null,
    });
    setSelectedImage(null);
    setPreview(null);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setProfileData((prevData) => ({
      ...prevData,
      result: {
        ...prevData.result,
        [name]:
          name === 'height' || name === 'weight' || name === 'weightGoals'
            ? parseInt(value, 10)
            : value,
      },
    }));
    setUpdatedData((prevData) => ({
      ...prevData,
      [name]:
        name === 'height' || name === 'weight' || name === 'weightGoals'
          ? parseInt(value, 10)
          : value,
    }));
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setSelectedImage(file);

      try {
        loadImage(file, { meta: true, canvas: true, orientation: true })
          .then((img) => {
            (img.image as any).toBlob((blob: any) => {
              const rotateFile = new File([blob], file.name, {
                type: file.type,
              });

              const reader = new FileReader();
              reader.onloadend = () => {
                setPreview(reader.result as string);
                console.log(reader.result as string);
                setUpdatedData((prevData) => ({
                  ...prevData,
                  profileImg: rotateFile, // 회전된 파일을 설정합니다.
                }));
              };

              reader.readAsDataURL(rotateFile);
            }, file.type);
          })
          .catch((error) => {
            alert(`이미지 처리 중 오류가 발생했습니다: ${error}`);
            console.error('Image processing error:', error);
          });
      } catch (error) {
        alert(`파일을 처리하는 동안 오류가 발생했습니다: ${error}`);
        console.error('File processing error:', error);
      }
    } else {
      setPreview(null);
    }
  };

  const handleSaveClick = () => {
    handleSave(updatedData);
    handleCancelClick();
  };

  const handleCancelClick = () => {
    handleSetInitialData();
    handleClose();
  };

  useEffect(() => {
    handleSetInitialData();
  }, [initialData]);

  return (
    <Modal open={open} onClose={handleCancelClick}>
      <Box
        sx={{
          position: 'absolute' as const,
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 400,
          bgcolor: 'background.paper',
          boxShadow: 24,
          p: 4,
          borderRadius: 2,
          maxHeight: '80vh',
          overflow: 'auto',
        }}>
        <Typography id='user-profile-modal-title' variant='h6' component='h2'>
          프로필 수정
        </Typography>
        <Grid container spacing={2} sx={{ mt: 2 }}>
          <Grid item xs={12} textAlign='center'>
            {preview ? (
              <Box
                component='img'
                src={preview}
                alt='Profile Preview'
                sx={{
                  width: '100%',
                  height: 'auto',
                  maxHeight: '300px',
                  objectFit: 'contain',
                  borderRadius: '10px',
                }}
              />
            ) : (
              <Typography variant='body2' color='textSecondary'>
                이미지 미리보기
              </Typography>
            )}
          </Grid>
          <Grid item xs={12}>
            <Button variant='contained' component='label' sx={{ mb: 2 }}>
              프로필 이미지 업로드
              <input
                type='file'
                hidden
                accept='image/*'
                onChange={handleFileChange}
              />
            </Button>
            <TextField
              fullWidth
              label='생일'
              name='birth'
              value={profileData.result.birth}
              onChange={handleChange}
              type='date'
              InputLabelProps={{ shrink: true }}
            />
          </Grid>
          <Grid item xs={12}>
            <Typography variant='body1' gutterBottom>
              성별
            </Typography>

            <RadioGroup
              value={profileData.result.gender}
              name='gender'
              onChange={(e) =>
                handleChange(e as React.ChangeEvent<HTMLInputElement>)
              }
              row>
              <FormControlLabel value='male' control={<Radio />} label='남성' />
              <FormControlLabel
                value='female'
                control={<Radio />}
                label='여성'
              />
            </RadioGroup>
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label='닉네임'
              name='nickname'
              value={profileData.result.nickname}
              onChange={handleChange}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label='키 (cm)'
              name='height'
              value={profileData.result.height}
              onChange={handleChange}
              type='number'
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label='몸무게 (kg)'
              name='weight'
              value={profileData.result.weight}
              onChange={handleChange}
              type='number'
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label='목표 몸무게 (kg)'
              name='weightGoals'
              value={profileData.result.weightGoals}
              onChange={handleChange}
              type='number'
            />
          </Grid>
        </Grid>
        <Box display='flex' justifyContent='flex-end' sx={{ mt: 2 }}>
          <Button onClick={handleSaveClick} variant='contained' color='primary'>
            저장
          </Button>
          <Button
            onClick={handleCancelClick}
            variant='outlined'
            color='secondary'
            sx={{ ml: 1 }}>
            취소
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};

export default memo(UserProfileModal);
