"use client";
import React, {useState} from "react";
import ChatLogWindowComponent from "@/app/_components/Chat/ChatLogWindowComponent/ChatLogWindowComponent";
import ChatSearchInput from "@/app/_components/Chat/ChatSearchInput/ChatSearchInput";
import styles from "./ChatComponent.module.css";
import { v4 as uuidv4 } from 'uuid';

const ChatComponent = () => {
  const [chatId, setChatId] = useState(uuidv4());
  const [finalPrompt, setFinalPrompt] = useState("");
  const [searchDisabled, setSearchDisabled] = useState(false);
  const handleSetPrompt = (prompt: string): void => {
    if (!searchDisabled) {
      setSearchDisabled(true);
      setFinalPrompt(prompt);
    }
  }
  return (
    <section className={styles.mainWrapperContainer}>
      <div className={styles.subWrapperContainer}>
        <ChatLogWindowComponent prompt={finalPrompt} chatId={chatId} enableSearch={() => setSearchDisabled(false)}/>
        <ChatSearchInput searchDisabled={searchDisabled} handleSetPrompt={handleSetPrompt}/>
      </div>
    </section>
  );
}

export default ChatComponent;
