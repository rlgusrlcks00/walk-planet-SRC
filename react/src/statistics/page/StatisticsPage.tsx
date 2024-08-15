import React, { useState, useEffect, memo } from 'react';
import {
  Container,
  Snackbar,
  Alert,
  Paper,
  Typography,
  useMediaQuery,
  Box,
} from '@mui/material';
import moment, { Moment } from 'moment';
import MainAppBar from '@components/MainAppBar';
import useLogout from '@hooks/useLogout';
import useSnackBar from '@hooks/useSnackBar';
import { useQuery, useQueryClient } from 'react-query';
import LogoutModal from '@components/LogoutModal';
import WeekPicker from '@components/WeekPicker';
import { getStatistics } from '@statistics/api/StatisticsApi';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
);

const WeeklyStepsPage: React.FC = () => {
  const isMobile = useMediaQuery('(max-width:600px)');
  const [selectedWeek, setSelectedWeek] = useState<{
    start: Moment;
    end: Moment;
  }>({
    start: moment().startOf('week'),
    end: moment().endOf('week').hour(23).minute(59).second(59),
  });

  const [startDateType, setStartDateType] = useState<string>('');
  const [endDateType, setEndDateType] = useState<string>('');

  const {
    openLogoutModal,
    handleLogoutModalOpen,
    handleLogoutModalClose,
    handleLogoutStart,
  } = useLogout();
  const {
    snackbarOpen,
    snackbarMessage,
    snackbarSeverity,
    showSnackbar,
    handleSnackbarClose,
  } = useSnackBar();
  const queryClient = useQueryClient();

  useEffect(() => {
    if (selectedWeek) {
      setStartDateType(selectedWeek.start.format('YYYY-MM-DDTHH:mm:ss'));
      setEndDateType(selectedWeek.end.format('YYYY-MM-DDTHH:mm:ss'));
    }
  }, [selectedWeek]);

  const { data, refetch } = useQuery(
    ['statistics', startDateType, endDateType],
    () => getStatistics(startDateType, endDateType),
    {
      enabled: !!startDateType && !!endDateType,
      onSuccess: (data) => {
        console.log('API 호출 결과:', data);
      },
      onError: (error) => {
        console.error('API 호출 에러:', error);
      },
    },
  );

  useEffect(() => {
    if (startDateType && endDateType) {
      refetch();
    }
  }, [startDateType, endDateType, refetch]);

  const maxStepGoals = data
    ? Math.max(...data.map((item) => item.todayStepGoals))
    : 0;

  const chartData = {
    labels: data?.map((item) => item.dayOfWeek),
    datasets: [
      {
        label: '걸음 수',
        data: data?.map((item) => item.stepCount),
        backgroundColor: 'rgba(54, 162, 235, 0.2)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1,
        stack: 'stack1',
      },
      {
        label: '남은 걸음 수',
        data: data?.map((item) => {
          const backgroundValue = maxStepGoals - item.stepCount;
          return backgroundValue < 0 ? 0 : backgroundValue;
        }),
        backgroundColor: 'rgba(200, 200, 200, 0.5)',
        borderColor: 'rgba(200, 200, 200, 1)',
        borderWidth: 1,
        stack: 'stack1',
      },
      {
        label: '목표 걸음 수',
        data: data?.map((item) => item.todayStepGoals),
        backgroundColor: 'rgba(255, 99, 132, 0.2)',
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: 1,
      },
    ],
  };

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        labels: {
          font: {
            size: isMobile ? 10 : 14,
          },
        },
        position: 'bottom' as const,
      },
      title: {
        display: true,
        text: '걸음 분석',
        font: {
          size: isMobile ? 16 : 20,
        },
        padding: {
          bottom: 20,
        },
      },
      tooltip: {
        callbacks: {
          label: function (context: any) {
            let label = context.dataset.label || '';

            if (label) {
              label += ': ';
            }
            if (context.parsed.y !== null) {
              label += context.parsed.y;
            }
            return label;
          },
          afterLabel: function (context: any) {
            const index = context.dataIndex;
            const item = data ? data[index] : null;
            if (item) {
              return [
                `날짜: ${moment(item.regDt).format('YYYY-MM-DD')}`,
                `걸음 수: ${item.stepCount}`,
                `목표 걸음 수: ${item.todayStepGoals}`,
              ];
            }
            return '';
          },
        },
      },
    },
    scales: {
      x: {
        stacked: true,
        title: {
          display: true,
          font: {
            size: isMobile ? 12 : 14,
          },
        },
        ticks: {
          font: {
            size: isMobile ? 10 : 12,
          },
        },
        grid: {
          display: false,
        },
      },
      y: {
        stacked: true,
        title: {
          display: true,
          font: {
            size: isMobile ? 12 : 14,
          },
        },
        ticks: {
          font: {
            size: isMobile ? 10 : 12,
          },
        },
        grid: {
          display: false,
        },
      },
    },
    layout: {
      padding: {
        top: 5,
        bottom: 5,
        left: 5,
        right: 5,
      },
    },
  };

  return (
    <Container maxWidth='lg' sx={{ mt: 4, mb: 4 }}>
      <MainAppBar onLogout={handleLogoutModalOpen} />
      <Paper
        sx={{
          p: 4,
          borderRadius: 2,
          boxShadow: 3,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          mt: 4,
        }}>
        <Box
          sx={{
            width: '100%',
            display: 'flex',
            alignItems: 'center',
            alignText: 'center',
            justifyContent: 'space-between',
          }}>
          <WeekPicker
            selectedWeek={selectedWeek}
            onWeekChange={setSelectedWeek}
          />
        </Box>
      </Paper>
      <Paper
        sx={{
          p: 1,
          borderRadius: 2,
          boxShadow: 3,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          mt: 4,
        }}>
        <Box
          sx={{
            position: 'relative',
            height: isMobile ? '40vh' : '50vh',
            width: '100%',
            mt: 2,
          }}>
          <Bar data={chartData} options={options} />
        </Box>
      </Paper>
      <LogoutModal
        open={openLogoutModal}
        handleClose={handleLogoutModalClose}
        handleLogout={handleLogoutStart}
      />
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={2500}
        onClose={handleSnackbarClose}>
        <Alert
          onClose={handleSnackbarClose}
          severity={snackbarSeverity}
          sx={{ width: '100%' }}>
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </Container>
  );
};

export default memo(WeeklyStepsPage);
