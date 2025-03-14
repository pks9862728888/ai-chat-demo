import {useEffect, useState} from "react";
import styles from "./ChatStreamComponent.module.css";
import {marked} from "marked";

const ChatStreamComponent = ({prompt, enableSearch}:
                             { prompt: string, enableSearch: () => void }) => {
  const [chatResponse, setChatResponse] = useState("");

  useEffect(() => {
    if (prompt) {
      const eventSource: EventSource = new EventSource(
        `http://localhost:8080/api/v1/chat/get-response?prompt='${prompt}'`);

      eventSource.onmessage = (event) => {
        setChatResponse((prev: string): string => prev + event.data.slice(1, -1));
      };

      eventSource.onerror = () => {
        if (!eventSource.CLOSED) { // Connection closed
          console.error("Error with chat streaming API: " + eventSource.readyState);
        }
        eventSource.close();
        enableSearch();
      };

      return () => {
        eventSource.close();
        enableSearch();
      };
    }
  }, [prompt]);

  return (
    <div
      className={styles.container}
      dangerouslySetInnerHTML={{__html: marked.parse(chatResponse)}}
    ></div>
  );
};

export default ChatStreamComponent;