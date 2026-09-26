import {Container} from "inversify";
import type {GreetingService} from "../../services/GreetingService.ts";
import {TYPES} from "../types/di.ts";
import {GreetingServiceImpl} from "../../services/impl/GreetingServiceImpl.ts";
import type {HttpClient} from "../api/HttpClient.ts";
import {DefaultHttpClient} from "../api/DefaultHttpClient.ts";
import {UserRepositoryImpl} from "../../repositories/impl/UserRepositoryImpl.ts";
import type {UserRepository} from "../../repositories/UserRepository.ts";
import {AuthServiceImpl} from "../security/impl/AuthServiceImpl.ts";
import type {AuthService} from "../security/AuthService.ts";
import type {UserService} from "../../services/UserService.ts";
import {UserServiceImpl} from "../../services/impl/UserServiceImpl.ts";

const container = new Container();

container.bind<HttpClient>(TYPES.HtpClient).to(DefaultHttpClient).inSingletonScope();

// repository
container.bind<UserRepository>(TYPES.UserRepository).to(UserRepositoryImpl).inSingletonScope();

// service
container.bind<AuthService>(TYPES.AuthService).to(AuthServiceImpl).inSingletonScope();
container.bind<UserService>(TYPES.UserService).to(UserServiceImpl).inSingletonScope();
container.bind<GreetingService>(TYPES.GreetingService).to(GreetingServiceImpl).inSingletonScope();

export {container};