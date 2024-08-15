import React, { memo, useState } from 'react';
import {
  Box,
  Button,
  Popover,
  Typography,
  Grid,
  IconButton,
} from '@mui/material';
import { ArrowBack, ArrowForward } from '@mui/icons-material';
import moment, { Moment } from 'moment';
import EditCalendarIcon from '@mui/icons-material/EditCalendar';
interface WeekPickerProps {
  selectedWeek: { start: Moment; end: Moment };
  onWeekChange: (week: { start: Moment; end: Moment }) => void;
}

moment.locale('ko'); // 한글 로케일 설정

const WeekPicker: React.FC<WeekPickerProps> = ({
  selectedWeek,
  onWeekChange,
}) => {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [currentMonth, setCurrentMonth] = useState(moment());

  const open = Boolean(anchorEl);
  const id = open ? 'simple-popover' : undefined;

  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const startOfMonth = currentMonth.clone().startOf('month').startOf('week');
  const endOfMonth = currentMonth.clone().endOf('month').endOf('week');
  const weeks: Moment[] = [];
  const currentWeek: Moment = startOfMonth;

  while (currentWeek <= endOfMonth) {
    weeks.push(currentWeek.clone());
    currentWeek.add(1, 'week');
  }

  const handleWeekClick = (week: Moment) => {
    if (week.isAfter(moment())) return; // 미래 날짜는 선택 불가
    onWeekChange({
      start: week.clone().startOf('week').hour(0).minute(0).second(0),
      end: week.clone().endOf('week').hour(23).minute(59).second(59),
    });
    handleClose();
  };

  const handlePrevMonth = () => {
    setCurrentMonth(currentMonth.clone().subtract(1, 'month'));
  };

  const handleNextMonth = () => {
    if (currentMonth.isAfter(moment(), 'month')) return; // 현재 월 이후로 이동 불가
    setCurrentMonth(currentMonth.clone().add(1, 'month'));
  };

  const renderDaysOfWeek = () => {
    const days = moment.weekdaysShort();
    return (
      <Grid container>
        {days.map((day) => (
          <Grid item xs key={day}>
            <Typography align='center' variant='body2'>
              {day}
            </Typography>
          </Grid>
        ))}
      </Grid>
    );
  };

  const renderWeeks = () => {
    return weeks.map((week) => {
      const isSelectedWeek = selectedWeek.start.isSame(week, 'week');
      return (
        <Grid container key={week.toString()} spacing={0.5}>
          <Grid
            container
            sx={{
              backgroundColor: isSelectedWeek ? 'primary.main' : 'transparent',
              borderRadius: isSelectedWeek ? '16px' : '0',
            }}>
            {Array(7)
              .fill(0)
              .map((_, i) => {
                const day = week.clone().add(i, 'day');
                const isFutureDate = day.isAfter(moment());
                return (
                  <Grid
                    item
                    xs
                    key={day.toString()}
                    sx={{ textAlign: 'center' }}>
                    <Box
                      sx={{
                        minWidth: '32px',
                        minHeight: '32px',
                        padding: '4px',
                        borderRadius: '50%',
                        backgroundColor: 'transparent',
                        color: isSelectedWeek ? '#fff' : 'text.primary',
                        fontSize: '0.875rem',
                        opacity: isFutureDate ? 0.5 : 1,
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        userSelect: 'none', // 텍스트 선택 비활성화
                      }}
                      onClick={() => handleWeekClick(week)}
                      data-text={day.date()} // 감지 방지를 위한 속성 추가
                    >
                      <span
                        style={{ userSelect: 'none', pointerEvents: 'none' }}>
                        {day.date()}
                      </span>
                    </Box>
                  </Grid>
                );
              })}
          </Grid>
        </Grid>
      );
    });
  };

  return (
    <Box>
      <IconButton onClick={handleClick}>
        <Typography variant='body1'>
          기간 : {selectedWeek.start.format('YYYY-MM-DD')} ~{' '}
          {selectedWeek.end.format('YYYY-MM-DD')}
        </Typography>
        <EditCalendarIcon />
      </IconButton>
      <Popover
        id={id}
        open={open}
        anchorEl={anchorEl}
        onClose={handleClose}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'left',
        }}
        PaperProps={{
          sx: { width: '100%', maxWidth: 300, p: 2 },
        }}>
        <Box
          display='flex'
          justifyContent='space-between'
          alignItems='center'
          mb={1}>
          <IconButton onClick={handlePrevMonth} size='small'>
            <ArrowBack />
          </IconButton>
          <Typography variant='body1'>
            {currentMonth.format('MMMM YYYY')}
          </Typography>
          <IconButton onClick={handleNextMonth} size='small'>
            <ArrowForward />
          </IconButton>
        </Box>
        {renderDaysOfWeek()}
        {renderWeeks()}
      </Popover>
    </Box>
  );
};

export default memo(WeekPicker);
