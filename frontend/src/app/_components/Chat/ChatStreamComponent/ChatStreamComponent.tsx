import {useEffect, useState} from "react";
import {ChatType} from "@/app/_type/ChatType";
import styles from "./ChatStreamComponent.module.css";
import {marked} from "marked";

marked.setOptions({
  gfm: true, // Enable GitHub Flavored Markdown (supports nested lists properly)
  breaks: true
});

const ChatStreamComponent = ({chatLog, chatId, enableSearch, fetchNewResponse}:
                             {
                               chatLog: ChatType,
                               chatId: string,
                               enableSearch: () => void,
                               fetchNewResponse: boolean
                             }) => {
  const [message, setMessage] = useState(chatLog.content);
  const prompt: string = chatLog.prompt;

  useEffect(() => {
    if (fetchNewResponse) {
      // Clear old stuff (if any)
      setMessage(chatLog.content);
      const eventSource: EventSource = new EventSource(
        `http://localhost:8080/api/v1/chat/get-response?prompt='${prompt}'&chatId=${chatId}`);

      eventSource.onmessage = (event) => {
        setMessage(prev => prev + event.data.slice(1, -1));
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
      };
    }
  }, [prompt, fetchNewResponse]);

  return (
    <div className={`${!chatLog.isResponse ? styles.requestChatBlockContainer : ""}`}>
      <div
        className={`${styles.chatBlock} ${!chatLog.isResponse ? styles.requestChatBlock : ""}`}
        dangerouslySetInnerHTML={{__html: marked.parse(message)}}
      ></div>
    </div>
  );
}

export default ChatStreamComponent;
