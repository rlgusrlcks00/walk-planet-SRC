import { createTheme } from '@mui/material';

const theme2 = createTheme({
  palette: {
    primary: {
      main: '#30343A', // 주요 색상을 좀 더 명확한 색상 코드로 변경
    },
    secondary: {
      main: '#00b295',
    },
    text: {
      primary: '#ffffff',
      secondary: '#ffffff',
    },
  },
  typography: {
    fontFamily: 'Roboto, sans-serif',
  },
  components: {
    MuiCssBaseline: {
      styleOverrides: {
        body: {
          backgroundColor: '#1c1e21',
          color: '#ffffff',
        },
      },
    },
    MuiDialog: {
      styleOverrides: {
        paper: {
          backgroundColor: '#30343A',
          color: '#ffffff',
        },
      },
    },
    MuiTypography: {
      styleOverrides: {
        root: {
          color: '#ffffff',
        },
      },
    },
    MuiButton: {
      styleOverrides: {
        root: {
          color: '#ffffff',
          backgroundColor: '#00b295',
          '&:hover': {
            backgroundColor: '#009d7f',
          },
        },
      },
    },
    MuiInputBase: {
      styleOverrides: {
        root: {
          color: '#ffffff',
        },
      },
    },
    MuiChip: {
      styleOverrides: {
        root: {
          color: '#ffffff',
        },
      },
    },
    MuiSwitch: {
      styleOverrides: {
        switchBase: {
          color: '#ffffff',
        },
        track: {
          backgroundColor: '#ffffff',
        },
      },
    },
    MuiSelect: {
      styleOverrides: {
        select: {
          backgroundColor: '#00b295',
          color: '#ffffff',
        },
        icon: {
          color: '#ffffff',
        },
      },
    },
    MuiMenuItem: {
      styleOverrides: {
        root: {
          backgroundColor: '#30343A',
          color: '#ffffff',
          '&.Mui-selected': {
            backgroundColor: '#00b295',
            '&:hover': {
              backgroundColor: '#00b295',
            },
          },
          '&:hover': {
            backgroundColor: 'rgba(48, 52, 58, 0.8)',
          },
        },
      },
    },
    MuiPopover: {
      styleOverrides: {
        paper: {
          backgroundColor: '#30343A',
          boxShadow: 'none',
        },
      },
    },
    MuiTabs: {
      styleOverrides: {
        root: {
          backgroundColor: '#30343A',
          boxShadow: 'none',
        },
      },
    },
    MuiButtonGroup: {
      styleOverrides: {
        root: {
          backgroundColor: '#30343A',
          boxShadow: 'none',
        },
      },
    },
    MuiTextField: {
      styleOverrides: {
        root: {
          backgroundColor: '#30343A',
          '& .MuiOutlinedInput-root': {
            '& fieldset': {
              borderColor: '#ffffff',
            },
            '&:hover fieldset': {
              borderColor: '#00b295',
            },
            '&.Mui-focused fieldset': {
              borderColor: '#00b295',
            },
            '& input': {
              color: '#ffffff',
              '&:focus': {
                color: '#00b295', // 포커스 상태에서 글자 색상을 설정합니다.
              },
            },
          },
        },
      },
    },
    MuiInputLabel: {
      styleOverrides: {
        root: {
          color: '#ffffff', // 기본 색상
          '&.Mui-focused': {
            color: '#00b295', // 포커스 시 색상
          },
        },
      },
    },
    MuiPaper: {
      styleOverrides: {
        root: {
          backgroundColor: '#424242', // 회색 배경색 설정
          color: '#ffffff',
        },
      },
    },
  },
});

export default theme2;
