import {RefObject, useEffect, useRef, useState} from "react";
import {ChatType} from "@/app/_type/ChatType";
import ChatStreamComponent from "@/app/_components/Chat/ChatStreamComponent/ChatStreamComponent";
import styles from "./ChatLogWindowComponent.module.css";

const ChatLogWindowComponent = ({prompt, chatId, enableSearch}:
                          { prompt: string, chatId: string, enableSearch: () => void }) => {
  const [chatLogs, setChatLogs] = useState<ChatType[]>([]);
  const chatIdIdx: RefObject<number> = useRef<number>(0);

  useEffect(() => {
    if (prompt) {
      setChatLogs((prev: ChatType[]) => {
        const chatIdReq: number = chatIdIdx.current + 1;
        const chatIdRes: number = chatIdReq + 1;
        chatIdIdx.current = chatIdRes;
        return [...prev,
          {chatIdIdx: chatIdReq, content: prompt, isResponse: false, prompt: ""}, // request
          {chatIdIdx: chatIdRes, content: "", isResponse: true, prompt: prompt} // response
        ]
      });
    }
  }, [prompt]);

  return (
    <section className={styles.chatSection}>
      {chatLogs.map((chatLog: ChatType) => {
        return (<ChatStreamComponent
          key={chatLog.chatIdIdx}
          chatId={chatId}
          chatLog={chatLog}
          enableSearch={enableSearch}
          fetchNewResponse={chatLog.chatIdIdx === chatLogs[chatLogs.length - 1].chatIdIdx && chatLog.isResponse}
        />);
      })}
    </section>
  );
};

export default ChatLogWindowComponent;