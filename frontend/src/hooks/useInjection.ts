import {createContext, useContext} from "react";
import {Container} from "inversify";
import {container} from "../core/config/inversify.config.ts";

const DIContext = createContext<Container>(container);

export function useInjection<T>(identifier: symbol): T {
    const ctxContainer = useContext(DIContext);
    return ctxContainer.get<T>(identifier);
}