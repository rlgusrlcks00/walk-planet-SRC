import React, { useEffect, useState } from 'react';

const TestPage: React.FC = () => {
  const [steps, setSteps] = useState<number>(0);

  // 메시지 이벤트를 수신하여 스텝 정보를 업데이트
  useEffect(() => {
    const handleMessage = (event: MessageEvent) => {
      try {
        const data = JSON.parse(event.data); // JSON 데이터를 파싱
        console.log('Received message:', data);
        if (data.steps !== undefined) {
          setSteps(data.steps);
        }
      } catch (error) {
        console.error('Failed to parse message data:', error);
      }
    };

    const listener: EventListener = (event) =>
      handleMessage(event as MessageEvent);

    document.addEventListener('message', listener);

    return () => {
      document.removeEventListener('message', listener);
    };
  }, []);

  return (
    <div style={styles.container}>
      <h1>Test Page</h1>
      <p>Steps: {steps}</p>
      <button onClick={() => window.location.reload()}>Reload</button>
    </div>
  );
};

const styles = {
  container: {
    textAlign: 'center' as const,
    padding: '20px',
  },
};

export default TestPage;
