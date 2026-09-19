import {Container} from "inversify";
import type {GreetingService} from "../services/GreetingService.ts";
import {TYPES} from "../types/di.ts";
import {GreetingServiceImpl} from "../services/impl/GreetingServiceImpl.ts";

const container = new Container();

container.bind<GreetingService>(TYPES.GreetingService).to(GreetingServiceImpl).inSingletonScope();

export {container};