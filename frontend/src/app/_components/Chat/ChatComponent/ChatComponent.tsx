"use client";
import React, {useState} from "react";
import ChatLogComponent from "@/app/_components/Chat/ChatLogComponent/ChatLogComponent";
import ChatSearchInput from "@/app/_components/Chat/ChatComponent/ChatSearchInput";

const ChatComponent = () => {
  const [finalPrompt, setFinalPrompt] = useState("");
  const [searchDisabled, setSearchDisabled] = useState(false);
  const handleSetPrompt = (prompt: string): void => {
    if (!searchDisabled) {
      setSearchDisabled(true);
      setFinalPrompt(prompt);
    }
  }
  return (
    <>
      <ChatLogComponent prompt={finalPrompt} enableSearch={() => setSearchDisabled(false)}/>
      <ChatSearchInput searchDisabled={searchDisabled} handleSetPrompt={handleSetPrompt}/>
    </>
  );
}

export default ChatComponent;
