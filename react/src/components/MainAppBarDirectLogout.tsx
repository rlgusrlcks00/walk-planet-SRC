import React from 'react';
import {
  AppBar,
  Toolbar,
  IconButton,
  Box,
  Button,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Divider,
  Drawer,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import LogoutIcon from '@mui/icons-material/Logout';
import { useNavigate } from 'react-router-dom';

const MainAppBarDirectLogout: React.FC = () => {
  const navigate = useNavigate();

  const [open, setOpen] = React.useState(false);
  const toggleDrawer = (newOpen: boolean) => () => {
    setOpen(newOpen);
  };

  const DrawerList = (
    <Box sx={{ width: 250 }} role='presentation' onClick={toggleDrawer(false)}>
      <List>
        {['Inbox', 'Starred', 'Send email', 'Drafts'].map((text, index) => (
          <ListItem key={text} disablePadding>
            <ListItemButton>
              <ListItemIcon></ListItemIcon>
              <ListItemText primary={text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
      <Divider />
    </Box>
  );

  const handleLogout = () => {
    // 로그아웃 로직을 여기에 추가합니다.
    localStorage.removeItem('token');
    localStorage.removeItem('name');
    localStorage.removeItem('email');
    navigate('/login');
  };

  return (
    <AppBar position='static'>
      <Toolbar sx={{ justifyContent: 'space-between', bgcolor: '#1c1e21' }}>
        <IconButton
          edge='start'
          color='inherit'
          aria-label='menu'
          onClick={toggleDrawer(true)}
          sx={{ mr: 2 }}>
          <MenuIcon />
        </IconButton>
        <Drawer open={open} onClose={toggleDrawer(false)}>
          {DrawerList}
        </Drawer>
        <Box sx={{ flexGrow: 1, display: 'flex', justifyContent: 'center' }}>
          <img
            src={`${process.env.PUBLIC_URL}/WalkMate_logo.png`}
            alt='WalkMate Logo'
            style={{ maxHeight: '100px' }}
          />
        </Box>
        <IconButton
          edge='start'
          color='inherit'
          aria-label='logout'
          onClick={() => {
            handleLogout();
          }}
          sx={{ mr: 2 }}>
          <LogoutIcon />
        </IconButton>
      </Toolbar>
    </AppBar>
  );
};

export default MainAppBarDirectLogout;
