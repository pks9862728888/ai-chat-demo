"use client";
import {useEffect, useState} from "react";

const ChatStreamComponent = () => {
  const [chatResponse, setChatResponse] = useState("");

  useEffect(() => {
    const promptText: string = "Tell me something about yourself?";
    const eventSource: EventSource = new EventSource(
      `http://localhost:8080/api/v1/chat/get-response?prompt='${promptText}'`);

    eventSource.onmessage = (event) => {
      setChatResponse((prev: string): string => prev + event.data.slice(1, -1));
    };

    eventSource.onerror = () => {
      if (!eventSource.CLOSED) { // Connection closed
        console.error("Error with chat streaming API: " + eventSource.readyState);
      }
      eventSource.close();
    };

    return () => {
      eventSource.close();
    };
  }, []);

  return (
    <div>
      <h1>Streaming Response</h1>
      <pre>Chat Response: {chatResponse}</pre>
    </div>
  );
};

export default ChatStreamComponent;