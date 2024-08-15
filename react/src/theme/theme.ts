import { createTheme } from '@mui/material';

const theme2 = createTheme({
  palette: {
    primary: {
      main: '#0288D1', // 밝은 파란색
    },
    secondary: {
      main: '#8BC34A', // 밝은 녹색
    },
    text: {
      primary: '#212121', // 짙은 회색
      secondary: '#757575', // 밝은 회색
    },
    background: {
      default: '#F5F5F5', // 밝은 회색
    },
  },
  typography: {
    fontFamily: 'Roboto, sans-serif',
  },
  components: {
    MuiCssBaseline: {
      styleOverrides: {
        body: {
          backgroundColor: '#F5F5F5', // 밝은 회색 배경
          color: '#212121', // 짙은 회색 글자
        },
      },
    },
    MuiDialog: {
      styleOverrides: {
        paper: {
          backgroundColor: '#FFFFFF', // 흰색 배경
          color: '#212121', // 짙은 회색 글자
        },
      },
    },
    MuiTypography: {
      styleOverrides: {
        root: {
          color: '#212121', // 짙은 회색 글자
        },
      },
    },
    MuiButton: {
      styleOverrides: {
        root: {
          color: '#FFFFFF', // 버튼 텍스트 색상 (흰색)
          backgroundColor: '#0288D1', // 버튼 배경색
          '&:hover': {
            backgroundColor: '#0277BD', // 호버 시 버튼 배경색
          },
          '&:disabled': {
            backgroundColor: '#B0BEC5', // 비활성화 시 버튼 배경색
            color: '#FFFFFF', // 비활성화 시 버튼 텍스트 색상
            cursor: 'not-allowed', // 비활성화 시 커서 스타일
          },
          '&:not(:disabled)': {
            cursor: 'pointer', // 활성화 시 커서 스타일
          },
        },
      },
    },
    MuiInputBase: {
      styleOverrides: {
        root: {
          color: '#212121', // 입력 텍스트 색상
        },
      },
    },
    MuiChip: {
      styleOverrides: {
        root: {
          color: '#FFFFFF', // 칩 텍스트 색상 (흰색)
          backgroundColor: '#0288D1', // 칩 배경색
        },
      },
    },
    MuiSwitch: {
      styleOverrides: {
        switchBase: {
          color: '#0288D1', // 스위치 색상
        },
        track: {
          backgroundColor: '#B3E5FC', // 트랙 색상
        },
      },
    },
    MuiSelect: {
      styleOverrides: {
        select: {
          backgroundColor: '#FFFFFF', // 선택 배경색
          color: '#212121', // 선택 텍스트 색상
        },
        icon: {
          color: '#0288D1', // 아이콘 색상
        },
      },
    },
    MuiMenuItem: {
      styleOverrides: {
        root: {
          backgroundColor: '#FFFFFF', // 메뉴 아이템 배경색
          color: '#212121', // 메뉴 아이템 텍스트 색상
          '&.Mui-selected': {
            backgroundColor: '#8BC34A', // 선택된 메뉴 아이템 배경색
            '&:hover': {
              backgroundColor: '#8BC34A', // 선택된 메뉴 아이템 호버 색상
            },
          },
          '&:hover': {
            backgroundColor: 'rgba(2, 136, 209, 0.1)', // 메뉴 아이템 호버 색상
          },
        },
      },
    },
    MuiPopover: {
      styleOverrides: {
        paper: {
          backgroundColor: '#FFFFFF', // 페이퍼 배경색
          boxShadow: 'none',
        },
      },
    },
    MuiTabs: {
      styleOverrides: {
        root: {
          backgroundColor: '#FFFFFF', // 탭 배경색
          boxShadow: 'none',
        },
      },
    },
    MuiButtonGroup: {
      styleOverrides: {
        root: {
          backgroundColor: '#FFFFFF', // 버튼 그룹 배경색
          boxShadow: 'none',
        },
      },
    },
    MuiTextField: {
      styleOverrides: {
        root: {
          backgroundColor: '#FFFFFF', // 텍스트 필드 배경색
          '& .MuiOutlinedInput-root': {
            '& fieldset': {
              borderColor: '#0288D1', // 필드셋 테두리 색상
            },
            '&:hover fieldset': {
              borderColor: '#0277BD', // 호버 시 필드셋 테두리 색상
            },
            '&.Mui-focused fieldset': {
              borderColor: '#0277BD', // 포커스 시 필드셋 테두리 색상
            },
            '& input': {
              color: '#212121', // 입력 텍스트 색상
              '&:focus': {
                color: '#0288D1', // 포커스 상태에서 입력 텍스트 색상
              },
            },
          },
        },
      },
    },
    MuiInputLabel: {
      styleOverrides: {
        root: {
          color: '#757575', // 기본 색상
          '&.Mui-focused': {
            color: '#0288D1', // 포커스 시 색상
          },
        },
      },
    },
    MuiPaper: {
      styleOverrides: {
        root: {
          backgroundColor: '#FFFFFF', // 페이퍼 배경색
          color: '#212121', // 텍스트 색상
        },
      },
    },
    MuiSvgIcon: {
      styleOverrides: {
        root: {
          color: '#0288D1', // 아이콘 기본 색상
        },
      },
    },
    MuiAppBar: {
      styleOverrides: {
        root: {
          backgroundColor: '#FFFFFF', // 툴바 배경색
          color: '#212121', // 툴바 텍스트 색상
        },
      },
    },
  },
});

export default theme2;
