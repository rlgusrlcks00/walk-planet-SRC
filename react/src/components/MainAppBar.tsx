import React, { memo } from 'react';
import {
  AppBar,
  Toolbar,
  IconButton,
  Box,
  List,
  ListItem,
  ListItemButton,
  ListItemText,
  Divider,
  Drawer,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import LogoutIcon from '@mui/icons-material/Logout';
import { useNavigate } from 'react-router-dom';
import { menuList } from '@components/MenuList';

interface MainAppBarProps {
  onLogout: () => void;
}
const MainAppBar: React.FC<MainAppBarProps> = ({ onLogout }) => {
  const navigate = useNavigate();

  const [open, setOpen] = React.useState(false);
  const toggleDrawer = (newOpen: boolean) => () => {
    setOpen(newOpen);
  };

  const handleMenuClick = (path: string) => {
    navigate(path);
    setOpen(false);
  };

  const handleLogoClick = () => {
    navigate('/');
    setOpen(false);
  };

  const DrawerList = (
    <Box sx={{ width: 250 }} role='presentation' onClick={toggleDrawer(false)}>
      <List>
        {menuList.map((item, index) => (
          <ListItem key={item.text} disablePadding>
            <ListItemButton onClick={() => handleMenuClick(item.path)}>
              <ListItemText primary={item.text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
      <Divider />
    </Box>
  );

  return (
    <AppBar position='static'>
      <Toolbar sx={{ justifyContent: 'space-between' }}>
        <IconButton
          edge='start'
          color='inherit'
          aria-label='menu'
          onClick={toggleDrawer(true)}>
          <MenuIcon />
        </IconButton>
        <Drawer open={open} onClose={toggleDrawer(false)}>
          {DrawerList}
        </Drawer>
        <Box
          sx={{ flexGrow: 1, display: 'flex', justifyContent: 'center' }}
          //onClick={handleLogoClick}
        >
          <img
            src={`${process.env.PUBLIC_URL}/WALKMATE_LOGO_TOP3.png`}
            alt='WalkMate Logo'
            style={{ maxHeight: '100px', maxWidth: '100px' }}
            onClick={handleLogoClick}
          />
        </Box>
        <IconButton
          edge='start'
          color='inherit'
          aria-label='logout'
          onClick={onLogout}>
          <LogoutIcon />
        </IconButton>
      </Toolbar>
    </AppBar>
  );
};

export default memo(MainAppBar);
